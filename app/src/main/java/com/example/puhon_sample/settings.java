package com.example.puhon_sample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import de.hdodenhof.circleimageview.CircleImageView;

import androidx.annotation.Nullable;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;


public class settings extends AppCompatActivity {

    TextView userEmail, userName;
    EditText FName, LName, PAge, PEmail, PPassword;
    Button SaveChangesBtn;
    ImageButton LogOut, AddProfilePic;
    FirebaseAuth fAuth;
    FirebaseDatabase database;
    DatabaseReference reference;
    String id;
    User userprofile;
    CircleImageView ImgProfilePic;
    private Uri imageUri;
    private String myUri = "";
    private StorageTask uploadTask;
    private StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        FName = findViewById(R.id.Settings_FName);
        LName = findViewById(R.id.Settings_LName);
        PAge = findViewById(R.id.Settings_Age);
        PEmail = findViewById(R.id.Settings_Email);
        PPassword = findViewById(R.id.Settings_Password);
        SaveChangesBtn = findViewById(R.id.Settings_SaveBtn);
        userEmail = findViewById(R.id.userEmail);
        userName = findViewById(R.id.userName);
        LogOut = findViewById(R.id.signOut);
        ImgProfilePic = findViewById(R.id.sampleImage);
        AddProfilePic = findViewById(R.id.settingsAddPhoto);


        fAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        FirebaseUser user = fAuth.getCurrentUser();
        assert user != null;
        id = user.getUid();
        reference = database.getReference().child("users").child(id);
        storageReference = FirebaseStorage.getInstance().getReference().child("Profile Pic");


        AddProfilePic.setOnClickListener(v -> CropImage.activity().setAspectRatio(1, 1).start(settings.this));

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                userprofile = snapshot.getValue(User.class);
                assert userprofile != null;
                FName.setText(userprofile.getUserFirstName());
                LName.setText(userprofile.getUserLastName());
                PAge.setText(userprofile.getUserAge());
                PEmail.setText(userprofile.getUserEmail());
                PPassword.setText(userprofile.getUserPassword());

                userEmail.setText(String.format(userprofile.getUserEmail()));
                userName.setText(String.format(userprofile.getUserFirstName(), userprofile.getUserLastName()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Updates user profile information + picture in settings page
        SaveChangesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (FName.getText().toString().isEmpty() || LName.getText().toString().isEmpty() ||
                        PAge.getText().toString().isEmpty() || PEmail.getText().toString().isEmpty() || PPassword.getText().toString().isEmpty()) {
                    Toast.makeText(settings.this, "One or Many fields are empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                String email = PEmail.getText().toString();
                user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Map<String, Object> edited = new HashMap<>();
                        edited.put("userFirstName", FName.getText().toString());
                        edited.put("userLastName", LName.getText().toString());
                        edited.put("userAge", PAge.getText().toString());
                        edited.put("userEmail", email);
                        reference.updateChildren(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(settings.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), menu.class));
                                finish();
                            }
                        });
                        Toast.makeText(settings.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(settings.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                String password = PPassword.getText().toString();
                user.updatePassword(password).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Map<String, Object> edited = new HashMap<>();
                        edited.put("userPassword", password);
                        reference.updateChildren(edited);
                    }

                });
                uploadProfileImage();
            }
        });

        // NavBar Buttons

        ImageButton btn_home = findViewById(R.id.nav_home);
        ImageButton btn_info = findViewById(R.id.nav_about_mood);
        ImageButton btn_progress = findViewById(R.id.nav_progress);

        btn_home.setOnClickListener(v -> {

            Intent intent = new Intent(this, menu.class);
            startActivity(intent);
        });

        btn_info.setOnClickListener(v -> {

            Intent intent = new Intent(this, aboutMoodscape.class);
            startActivity(intent);
        });

        btn_progress.setOnClickListener(v -> {

            Intent intent = new Intent(this, summary.class);
            startActivity(intent);
        });

        LogOut.setOnClickListener(v -> {
            fAuth.signOut();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        });

        getUseInfo();
    }

    private void getUseInfo() {
        reference.child(fAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0)
                {

                    if (dataSnapshot.hasChild("image"))
                    {
                        String image = dataSnapshot.child("image").getValue().toString();
                        Picasso.get().load(image).into(ImgProfilePic);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
        }
        else
        {
            Toast.makeText(this, "Error, Try Again", Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadProfileImage() {
        if (imageUri != null) {
            final StorageReference fileRef = storageReference
                    .child(fAuth.getCurrentUser().getUid() + ".jpg");

            uploadTask = fileRef.putFile(imageUri);

            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener((OnCompleteListener<Uri>) task -> {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();
                    myUri = downloadUri.toString();

                    HashMap<String, Object> userMap = new HashMap<>();
                    userMap.put("image", myUri);

                    reference.child(fAuth.getCurrentUser().getUid()).updateChildren(userMap);

                }
            });
        }
    }
}




