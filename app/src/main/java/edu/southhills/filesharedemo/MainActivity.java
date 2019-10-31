package edu.southhills.filesharedemo;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.ImageView;
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

            // cursor to query returned image
            Cursor returnCursor =
                    getContentResolver().query(returnUri, null, null, null, null);

            // get column indices of the data in the cursor
            int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
            returnCursor.moveToFirst();

            // build string with data from the cursor
            String fileInfo = "FileName:\n" + returnCursor.getString(nameIndex) +
                    "\n" + returnCursor.getLong(sizeIndex) + " bytes";

            TextView tvFileInfo = findViewById(R.id.tvFileInfo);
            tvFileInfo.setText(fileInfo);

            // display returned image
            ImageView ivThumbNail = findViewById(R.id.imgFileThumbNail);
            ivThumbNail.setImageURI(returnUri);

        }
    }
}
