package com.example.sean.lastemotion;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText emotion;
    EditText explanation;
    String emotionText;
    String explanationText;
    String combinedText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emotion = (EditText) findViewById(R.id.eTEmotion);
        explanation = (EditText) findViewById(R.id.eTExplanation);

        load();
    }

    private void load() {
        try {
            FileInputStream fis = openFileInput("savedEmotion.txt");
            int read = -1;
            StringBuffer buffer = new StringBuffer();
            //read the bytes one by one and save it into variable "read". At the end of the txt file,
            //read returns -1 by default; hence the while loop.
            while ((read = fis.read()) != -1) {
                buffer.append((char) read);
            }
            String emotionText = buffer.substring(0, buffer.indexOf("/br/"));
            String explanationText = buffer.substring(buffer.indexOf("/br/") + 4);
            emotion.setText(emotionText);
            explanation.setText(explanationText);
            Toast.makeText(this, "Load successful", Toast.LENGTH_LONG).show();
            fis.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "Load unsuccessful. No save file found", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, "IOException", Toast.LENGTH_LONG).show();
        }
    }

    public void onClickSave(View v) {
        emotionText = emotion.getText().toString();
        explanationText = explanation.getText().toString();
        combinedText = emotionText + "/br/" + explanationText;

        try {
            FileOutputStream fos = openFileOutput("savedEmotion.txt", Context.MODE_PRIVATE);
            fos.write(combinedText.getBytes());
            fos.close();
            Toast.makeText(this, "Save completed", Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            Toast.makeText(this, "File not found", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Toast.makeText(this, "IOException", Toast.LENGTH_LONG).show();
        }
    }
}
