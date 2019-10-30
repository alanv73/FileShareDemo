package edu.southhills.filesharedemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void requestFiles(View v){
        Intent mRequestFileIntent = new Intent(Intent.ACTION_PICK);
        mRequestFileIntent.setType("image/*");
        startActivityForResult(mRequestFileIntent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable Intent returnIntent) {
        super.onActivityResult(requestCode, resultCode, returnIntent);

        // If the selection didn't work
        if (resultCode != RESULT_OK) {
            // Exit without doing anything else
            return;
        } else {
            // Get the file's content URI from the incoming Intent
            Uri returnUri = returnIntent.getData();

            String mimeType = getContentResolver().getType(returnUri);

            TextView textView = findViewById(R.id.textView);
            textView.setText(mimeType + " : " + returnUri.toString());
        }
    }
}
