package de.planetmuk.android.filestoragedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    // Der Name, den wir dem Datenfile geben möchten
    final String myFileName = "mydata.txt";

    // 3 Eingabfelder
    private EditText nameField, emailField, phoneField;

    // 3 Datenfelder, die innerhalb der Instanz die aktuellen Daten enthalten
    private String name, email, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Verweise auf die drei Eingabefelder holen
        nameField = findViewById(R.id.editTextName);
        emailField = findViewById(R.id.editTextEmail);
        phoneField = findViewById(R.id.editTextPhone);

        // Daten aus dem Datenfile in die Datenfeldern name, email, phone einlesen
        readDataFromFile();

        // Inhalte der Datenfelder name, email, phone anzeigen
        displayData();
    }

    // Eventhandling für den SPEICHERN-Button
    public void onClick_ButtonSave (View v){

        // Eingaben aus den drei Feldern auslesen
        name = nameField.getText().toString();
        email = emailField.getText().toString();
        phone = phoneField.getText().toString();

        // und in unsere Datei schreiben.
        writeDataToFile();

        Toast.makeText(this,"Daten gespeichert", Toast.LENGTH_SHORT).show();
    }

    // Eventhandling für den LÖSCHEN-Button
    public void onClick_ButtonDelete (View v){

        // Die Datenfelder werden gelöscht (durch Leerstrings ersetzt)
        name="";
        email="";
        phone="";

        // und in unsere Datei geschrieben
        writeDataToFile();

        // Die aktuellen (leeren) Felder werden angezeigt
        displayData();

        Toast.makeText(this,"Daten gelöscht", Toast.LENGTH_SHORT).show();
    }

    // Eventhandling für den ABBRECHEN-Button
    public void onClick_ButtonCancel (View v){

        // Egal, was der Benutzer getippt hat: Die letzten Werte aus den Datenfeldern werden wieder angezeigt
        displayData();
        Toast.makeText(this,"Aktion abgebrochen", Toast.LENGTH_SHORT).show();
    }

    // Diese Methode liest die in unserer Datei gespeicherten Werte ein
    public void readDataFromFile(){
        try {
            // Ein-/Ausgabe in Java ist mühsam ....
            // Wer benötigen zunächst einen InputStream zu unserer Datei
            FileInputStream myInputStream = openFileInput(myFileName);
            // auf diesen setzen wir einen InputStreamReader
            InputStreamReader myInputStreamReader = new InputStreamReader(myInputStream);
            // und auf diesen wiederum einen BufferedReader (Zeilenleser)
            BufferedReader myBufferedReader = new BufferedReader(myInputStreamReader);
            // Jetzt können wir Zeile für Zeile aus der Datei lesen
            name = myBufferedReader.readLine();
            email = myBufferedReader.readLine();
            phone = myBufferedReader.readLine();
        } catch (FileNotFoundException e) {
            Toast.makeText(this,"Fehler: Datei nicht gefunden", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (IOException e) {
            Toast.makeText(this,"Fehler beim Lesen der Daten", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    // Diese Methode schreibt Daten in unsere Datei
    public void writeDataToFile(){
        File myDataFile;
        try{
            // Wir versuchen mal, ob wir die Datei zum Lesen öffnen können
            FileInputStream myInputStream = openFileInput(myFileName);
        } catch (FileNotFoundException e) {
            // Die Datei wurde nicht gefunden und muss daher von uns neu angelegt werden
            myDataFile = new File(getFilesDir(), myFileName);
            Toast.makeText(this,"Datendatei " + myFileName + " wurde neu angelegt", Toast.LENGTH_SHORT).show();
        }
        try {
            // Wir legen die Datei neu zum Schreiben an
            myDataFile = new File(getFilesDir(), myFileName);
            // Zum Schreiben benötigen wir einen FileOutputStream auf der Datei
            FileOutputStream myFileOutputStream = new FileOutputStream(myDataFile);
            // auf diesen setzen wir einen OutputStreamWriter
            OutputStreamWriter myOutputStreamWriter = new OutputStreamWriter(myFileOutputStream);
            // und auf diesen wiederum einen BufferedWriter, der zeilenweise schreiben kann
            BufferedWriter myBufferedWriter = new BufferedWriter(myOutputStreamWriter);
            // Jetzt geben wir Zeile für Zeile die Datenfelder in die Datei aus
            myBufferedWriter.write(name);
            myBufferedWriter.newLine();
            myBufferedWriter.write(email);
            myBufferedWriter.newLine();
            myBufferedWriter.write(phone);
            myBufferedWriter.newLine();
            // Fertig ausgeben und Datei schließen
            myBufferedWriter.flush();
            myBufferedWriter.close();
            Toast.makeText(this,"Datei erfolgreich beschrieben", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Hilfsmethode, um die aktuellen Datenfelder in die Anzeigefelder zu schreiben.
    public void displayData(){
        nameField.setText(name);
        emailField.setText(email);
        phoneField.setText(phone);
    }
}