/**
 * Class for the main Traffic Collector Activity.
 * @author Fratila Catalin Ionut
 */

package ro.pub.acs.traffic.collector;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.json.JSONArray;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.PowerManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TrafficCollector extends Activity {
	private Button start, pause, upload;
	private TextView statusText;
	
	private String status;
	
	private Toast toast;
	private long lastBackPressTime = 0;
	
	private Intent collector;
	
	private LocationManager locationManager;
	
	private Thread uploadThread;
	
	Handler toastHandler = new Handler();
    
    private Database db;
    
    private Activity thisActivity;
    
    private PowerManager powerManager;
    private PowerManager.WakeLock uploadWakeLock;
    
    public static final String DATE_FORMAT_NOW = "yyyy_MM_dd_HH_mm_ss";
    
    Runnable toastRunnableStart = new Runnable() {
    	public void run() {
    		statusText.setText("Upload Status: Running");
    		Toast.makeText(getApplicationContext(), "Upload started", Toast.LENGTH_LONG)
    			.show();
    	}
    };
    
    Runnable toastRunnableError = new Runnable() {
    	public void run() {
    		statusText.setText("Upload Status: Sending Error! Please try again!");
    		Toast.makeText(getApplicationContext(), "Upload Error", Toast.LENGTH_LONG)
    			.show();
    	}
    };
    
    Runnable toastRunnableNoData = new Runnable() {
    	public void run() {
    		Toast.makeText(getApplicationContext(), "No Data to Upload", Toast.LENGTH_LONG)
    			.show();
        }
    };
    
    Runnable toastRunnableFinish = new Runnable() {
    	public void run() {
    		statusText.setText("Upload Status: Finished");
    		Toast.makeText(getApplicationContext(), "Upload finished", Toast.LENGTH_LONG)
    			.show();
    	}
    };
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		powerManager = (PowerManager)getSystemService(Context.POWER_SERVICE);
		uploadWakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "UploadWL");
		
		setContentView(R.layout.main);
		
		start = (Button) this.findViewById(R.id.bstart);
		pause = (Button) this.findViewById(R.id.bpause);
		upload = (Button) this.findViewById(R.id.bupload);
		
		start.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				pause.setClickable(true);
				HandleStart();
			}
		});
		
		pause.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				HandlePause();
			}
		});
		pause.setClickable(false);
		upload.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				HandleUpload();
			}
		});
		
		status = "none";
		statusText = (TextView)findViewById(R.id.status);
        statusText.setText("Upload Status: " + status);
		
		thisActivity = this;
	}
	
	public void HandleStart() {
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		
		boolean isGPS = locationManager.isProviderEnabled (LocationManager.GPS_PROVIDER);
		
		if(!isGPS) {
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setMessage("Please enable GPS to use application");
			alertDialog.setButton("GPS Settings", new DialogInterface.OnClickListener() {
			      public void onClick(DialogInterface dialog, int which) {
			    	  startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 0);
			      }
			});
			alertDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {
			      public void onClick(DialogInterface dialog, int which) {
			    	  dialog.cancel();
			      }
			});
			alertDialog.show();
			
		}
		
		collector = new Intent(this, CollectorService.class);
		startService(collector);
	}
	
	public void HandlePause() {
		pause.setClickable(false);
		if(collector != null)
			stopService(collector);
	}

	@Override
	public void onBackPressed() {
		if (this.lastBackPressTime < System.currentTimeMillis() - 4000) {
			toast = Toast.makeText(this, "Press back again to close this app", 4000);
			toast.show();
			this.lastBackPressTime = System.currentTimeMillis();
		} else {
			if (toast != null) {
				toast.cancel();
			}
			if(collector != null)
				stopService(collector);
			System.exit(0);
		}
	}
	
	private void HandleUpload() {
		if(!Utils.checkInternetConnection(thisActivity)){ 
			AlertDialog alertDialog = new AlertDialog.Builder(this).create();
			alertDialog.setMessage("Please enable internet connection to use upload function");
			alertDialog.setButton("Wireless And Network Settings", new DialogInterface.OnClickListener() {
			      public void onClick(DialogInterface dialog, int which) {
			    	  startActivityForResult(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS), 0);
			      }
			});
			alertDialog.setButton2("Cancel", new DialogInterface.OnClickListener() {
			      public void onClick(DialogInterface dialog, int which) {
			    	  dialog.cancel();
			      }
			});
			alertDialog.show();
		}
		else {
			uploadThread = new UploadThread();
			uploadThread.start();
		}
    }
	
	public void onStop() {
		super.onStop();
	}
	
	public void removeData(Database db) {
		db.clearTable();
		if(Utils.testCard()) {
			File sdCard = Environment.getExternalStorageDirectory();
			File dir = new File (sdCard.getAbsolutePath() + "/collector/");
			File file = new File(dir, "journey.txt");
			if(file.exists())
				file.delete();
		}
	}
	
	class UploadThread extends Thread {
		public void run() {
			boolean error = false;
			
			uploadWakeLock.acquire();
			
			db = new Database(thisActivity, "collector", "routes", new String[] { "lat", "long", "speed", "timestamp" });
			for(int i = 0; i < 1000; i++)
				db.insert(new String[]{"23", "23", "1", "1"});
			JSONArray elements = db.getListJson("");
			if(elements.length() != 0)
			{
				toastHandler.post(toastRunnableStart);
				
				String toSend = elements.toString();
				try {
					URL url = new URL("http://cipsm.hpc.pub.ro/MACollector/collector.php");
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	
					connection.setDoInput(true);
					connection.setDoOutput(true);
					connection.setUseCaches(false);
					connection.setRequestMethod("POST");
	
					connection.setRequestProperty("Connection", "Keep-Alive");
					connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
	
					DataOutputStream dataOut = new DataOutputStream(connection.getOutputStream());
					Calendar cal = Calendar.getInstance();
				    SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
					String filename = "journey" + URLEncoder.encode(sdf.format(cal.getTime()), "UTF-8") + ".txt";
					
					dataOut.writeBytes("filename=" + filename + "&elements=" + URLEncoder.encode(toSend, "UTF-8"));
					dataOut.flush();
					
					BufferedReader dataIn = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				    String line = dataIn.readLine();
				    System.out.println(line);
				    if(line == null) {
				    	error = true;
				    	toastHandler.post(toastRunnableError);
				    	db.close();
				    } else if(!line.equals("200") ) {
				    	error = true;
				    	toastHandler.post(toastRunnableError);
				    	db.close();
				    }
				    dataOut.close();
				    dataIn.close();
				} catch (Exception e) {
					error = true;
					toastHandler.post(toastRunnableError);
			    	db.close();
					e.printStackTrace();
				}
				if(!error) {
					toastHandler.post(toastRunnableFinish);
					removeData(db);
					db.close();
				}
			}
			else {
				toastHandler.post(toastRunnableNoData);
				db.close();
			}
			if (uploadWakeLock.isHeld())
				uploadWakeLock.release();
		}
	}
}