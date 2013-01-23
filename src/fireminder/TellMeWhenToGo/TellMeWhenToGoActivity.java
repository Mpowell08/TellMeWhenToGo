package fireminder.TellMeWhenToGo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import GoogleMatrixAPIHelpers.GoogleDMQuery;
import GoogleMatrixAPIHelpers.GoogleDMResult;
import GoogleMatrixAPIHelpers.QueryExecutor;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

public class TellMeWhenToGoActivity extends Activity {
	
	GoogleDMResult result;
	EditText destination_et;
	EditText origin_et;
	TextView time_tv;
	TextView date_tv;
	Button done_bt;
	
	CalendarArrivalGen arrivalGen = new CalendarArrivalGen();
	//Date and Time Listeners
	final OnDateSetListener odsl = new OnDateSetListener(){
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			arrivalGen.setDay(dayOfMonth);
			arrivalGen.setMonth(monthOfYear);
			arrivalGen.setYear(year);
			
			date_tv.setText("" + (monthOfYear+1) + "/" + dayOfMonth + "/" + year);
			
			Log.d("day", "" + dayOfMonth);
			Log.d("year", "" + year);
			Log.d("month", "" + monthOfYear);
		}
	};
	
	final OnTimeSetListener otsl = new OnTimeSetListener(){
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			arrivalGen.setHour(hourOfDay);
			arrivalGen.setMinute(minute);
			
			if(hourOfDay == 0){
				time_tv.setText("12" + ":" + minute + "AM");
			}
			else if(hourOfDay > 12){
				time_tv.setText("" + hourOfDay % 12 + ":" + minute + "PM");
			} else {
				time_tv.setText("" + hourOfDay + ":" + minute + "AM");
			}
			
			Log.d("hour", "" + hourOfDay);
			Log.d("minute", "" + minute);
		}
	};
	
    @Override
    public void onCreate(Bundle b) {
    	super.onCreate(b);
        setContentView(R.layout.activity_main);
        SetupInit();
        
       // GoogleDMQuery query = new GoogleDMQuery("Antioch, CA", "Walnut Creek");
     //   QueryExecutor qe = new QueryExecutor(query);
       // GoogleDMResult result = qe.createResult();
        
        }
    
    public void onPause(){
    	super.onPause();
    }
    public void SetupInit(){
    	destination_et = (EditText) findViewById(R.id.fourth_row_edittext);
    	origin_et = (EditText) findViewById(R.id.top_row_edittext);
    	time_tv = (TextView) findViewById(R.id.second_row_timeview);
    	date_tv = (TextView) findViewById(R.id.third_row_dateview);
    	done_bt = (Button) findViewById(R.id.done_button);
    	
    	//Setup initial values for time and date
    	Calendar cal = Calendar.getInstance();
    	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy");
    	SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm aa");
    	time_tv.setText(timeFormat.format(cal.getTime()));
    	date_tv.setText(dateFormat.format(cal.getTime()));
    	
    }
    
    public void DateRowClicked(View v){
    	Calendar cal = Calendar.getInstance();
		DatePickerDialog datePickDiag = new DatePickerDialog(this, odsl, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
		datePickDiag.show();
    }
    
    public void TimeRowClicked(View v){
    	Calendar cal = Calendar.getInstance();
		TimePickerDialog timePickDiag = new TimePickerDialog(this, otsl, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE),false);
		timePickDiag.show();
    }
    
    public void OnDoneButtonClicked(View v){
    	GoogleDMQuery query = new GoogleDMQuery(origin_et.getText().toString(),destination_et.getText().toString());
    	QueryExecutor qe = new QueryExecutor(query);
    	GoogleDMResult result = null;
    	DoTheQuery dtq = (DoTheQuery) new DoTheQuery().execute(qe);
    	try {
			result = dtq.get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Calendar calendar = arrivalGen.doMath(result.getDuration());
    	SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy hh:mm aa");
		String calendarDateString = dateFormat.format(calendar.getTime());
    	
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		if(result.getStatus().contains("OK")){
			builder.setMessage("Going from " + result.getOrigin() + 
					" to " + result.getDestination() + " will take " + result.getDuration()/60 + " minutes. ");
	    	builder.setTitle("Set Reminder for " + calendarDateString + "?");
		} else {
			builder.setMessage("Sorry, that address was not found.");
		}
	    	builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	    	
				@Override
				public void onClick(DialogInterface dialog, int which) {
						
				}
			});
		
    	AlertDialog dialog = builder.create();
    	dialog.show();
    	
    	Log.d("DoneClicked", "" + destination_et.getText().toString());
    	Log.d("DoneClicked", "" + origin_et.getText().toString());
    	
    }
    private class DoTheQuery extends AsyncTask<QueryExecutor, Void, GoogleDMResult>{

		@Override
		protected GoogleDMResult doInBackground(QueryExecutor... arg0) {
			// TODO Auto-generated method stub
			return arg0[0].createResult();
		}
		
		protected void onPostExecute(GoogleDMResult result){
	    	Log.d("Results", result.toString());
		}
    	
    }
}
