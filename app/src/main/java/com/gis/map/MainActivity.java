package com.gis.map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
    private static final ExampleStartInfo[] data = {
            new ExampleStartInfo(SimpleExampleActivity.class, "Simple"),
            new ExampleStartInfo(ConfigureExampleActivity.class, "Configuration"),
    };

    private boolean available;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter<ExampleStartInfo> adapter = new ArrayAdapter<ExampleStartInfo>(this,
                android.R.layout.simple_expandable_list_item_1,data);

        ListView listView = (ListView) findViewById(R.id.main_listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startExample(position);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        available = GooglePlayServicesErrorDialogFragment.showDialogIfNotAvailable(this);
    }

    private void startExample(int position) {
        Class<? extends Activity> cls = data[position].activityClass;
        if (available) {
            Intent intent = new Intent(this, cls);
            startActivity(intent);
        } else {
            Toast.makeText(getApplication(), "Not available", Toast.LENGTH_SHORT).show();
        }
    }

    private static class ExampleStartInfo {
        private final Class<? extends Activity> activityClass;
        private final String displayName;

        public ExampleStartInfo(Class<? extends Activity> activityClass, String displayName) {
            this.activityClass = activityClass;
            this.displayName = displayName;
        }

        @Override
        public String toString() {
            return displayName;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
