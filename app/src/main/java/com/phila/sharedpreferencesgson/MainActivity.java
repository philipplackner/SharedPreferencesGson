package com.phila.sharedpreferencesgson;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.PropertyPermission;

public class MainActivity extends AppCompatActivity {

    private static final String PREFERENCE_NAME = "myPreferences";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnSave = findViewById(R.id.btnSave);
        Button btnLoad = findViewById(R.id.btnLoad);
        final EditText etFirstName = findViewById(R.id.etFirstName);
        final EditText etLastName = findViewById(R.id.etLastName);
        final EditText etAge = findViewById(R.id.etAge);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person person = new Person(etFirstName.getText().toString(),
                        etLastName.getText().toString(),
                        Integer.parseInt(etAge.getText().toString()));
                savePerson("myPerson", person);
                Toast.makeText(MainActivity.this,
                        "Saved the person", Toast.LENGTH_SHORT).show();
            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Person person = loadPerson("myPerson");
                etFirstName.setText(person.getFirstName());
                etLastName.setText(person.getLastName());
                etAge.setText(person.getAge() + "");
            }
        });
    }

    private void savePerson(String key, Person person) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE).edit();

        Gson gson = new Gson();
        String personString = gson.toJson(person);
        editor.putString(key, personString);
        editor.apply();
    }

    private Person loadPerson(String key) {
        SharedPreferences sharedPref = getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE);

        Gson gson = new Gson();
        String gsonString = sharedPref.getString("myPerson", null);
        return gson.fromJson(gsonString, Person.class);
    }

    public class Person {

        private String firstName, lastName;
        private int age;

        public Person(String firstName, String lastName, int age) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.age = age;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public int getAge() {
            return age;
        }
    }
}
