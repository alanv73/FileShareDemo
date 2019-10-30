package edu.southhills.filesharedemo;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

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

            String fileInfo = returnCursor.getString(nameIndex) +
                    "\n" + returnCursor.getLong(sizeIndex) + " bytes";

            TextView tvFileInfo = findViewById(R.id.tvFileInfo);
            tvFileInfo.setText(fileInfo);

            Bitmap imageBitmap = null;
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), returnUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ImageView ivThumbNail = findViewById(R.id.imgFileThumbNail);
            ivThumbNail.setImageBitmap(imageBitmap);
//            ivThumbNail.setImageURI(returnUri);

        }
    }
}
