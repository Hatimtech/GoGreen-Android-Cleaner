package com.gogreencleaner.main.screens;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;

import android.os.Bundle;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;

import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gogreencleaner.main.R;
import com.gogreencleaner.main.Utils.CommonUtils;
import com.gogreencleaner.main.Utils.Constants;
import com.gogreencleaner.main.Utils.Preferences.PrefEntity;
import com.gogreencleaner.main.Utils.Preferences.Preferences;
import com.gogreencleaner.main.Volley.APIUtility;
import com.gogreencleaner.main.model.UpdateInfo.REQUEST.UpdateInfoRequest;
import com.gogreencleaner.main.model.UpdateInfo.RESPONSE.UpdateInfoResponseWrapper;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class UpdateInfoActivity extends BaseActivity implements View.OnClickListener {

    APIUtility apiUtility;
    private Uri capturedImageUri;
    Toolbar toolbar;
    AppCompatTextView title;
    private Button updateButtn;
    private EditText firstName;
    private EditText lastName;
    private ImageView profilePic, back;
    String encodedString = "";
    private Button selectPic;
    String cameraPicturePath = null, galleryPicturePath = null, randomString, fileName = null, imagePath, path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);
        apiUtility = new APIUtility(UpdateInfoActivity.this);
        intiViews();
    }

    private void intiViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (AppCompatTextView) findViewById(R.id.title);
        title.setText(R.string.updateProfile);
        firstName = findViewById(R.id.et_first_name);
        lastName = findViewById(R.id.et_last_name);
        firstName.setText(Preferences.getPreference(getApplicationContext(), PrefEntity.USER_NAME));
        lastName.setText(Preferences.getPreference(getApplicationContext(), PrefEntity.LAST_NAME));
        profilePic = findViewById(R.id.profile_img_post);
        selectPic = findViewById(R.id.btn_chose_image);
        back = findViewById(R.id.back);
        back.setOnClickListener(this);
        selectPic.setOnClickListener(this);
        updateButtn = findViewById(R.id.btn_update);
        updateButtn.setOnClickListener(this);
        if (Preferences.getPreference(getApplicationContext(), PrefEntity.IMAGE).equals("")) {
            profilePic.setImageResource(R.mipmap.placeholder);
        } else {
            byte[] decodedString = Base64.decode(Preferences.getPreference(getApplicationContext(),PrefEntity.IMAGE), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            profilePic.setImageBitmap(decodedByte);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;

            case R.id.btn_chose_image:
                selectPicture();
                break;


            case R.id.btn_update:
                validation();
                break;
        }
    }

    private void validation() {

        if (!TextUtils.isEmpty(firstName.getText().toString().trim())) {

            if (!TextUtils.isEmpty(lastName.getText().toString().trim())) {

                updateInfo(firstName.getText().toString(), lastName.getText().toString());

            } else {
                lastName.setError(getString(R.string.lastnameAlert));
            }
        } else {
            firstName.setError(getString(R.string.firstnameAlert));
        }
    }


    void updateInfo(final String firstName, final String lastName) {

        UpdateInfoRequest updateInfoRequest = new UpdateInfoRequest();
        updateInfoRequest.setAppKey(Constants.APPKEY);
        updateInfoRequest.setEmail(Preferences.getPreference(getApplicationContext(), PrefEntity.USER_EMAIL));
        updateInfoRequest.setLastName(lastName);
        updateInfoRequest.setFirstName(firstName);
        updateInfoRequest.setUserId(Preferences.getPreference(getApplicationContext(), PrefEntity.USERID));
        updateInfoRequest.setMethod("update_info");
        updateInfoRequest.setPhoneNumber(Preferences.getPreference(getApplicationContext(), PrefEntity.PHONE_NUMBER));

        if(encodedString.equals("")){
            updateInfoRequest.setImage(Preferences.getPreference(getApplicationContext(),PrefEntity.IMAGE));
        }else {
            updateInfoRequest.setImage(encodedString);
        }

        apiUtility.updateUserInfo(UpdateInfoActivity.this, updateInfoRequest, true, new APIUtility.APIResponseListener<UpdateInfoResponseWrapper>() {
            @Override
            public void onReceiveResponse(UpdateInfoResponseWrapper response) {
                if (response != null) {
                        Toast.makeText(UpdateInfoActivity.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                    Preferences.setPreference(getApplicationContext(), PrefEntity.IMAGE, encodedString);
                    Preferences.setPreference(getApplicationContext(), PrefEntity.USER_NAME,firstName);
                    Preferences.setPreference(getApplicationContext(), PrefEntity.LAST_NAME, lastName);
                    Intent intent = new Intent(UpdateInfoActivity.this, DashBoardActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                }
            }

            @Override
            public void onResponseFailed() {
                CommonUtils.alert(UpdateInfoActivity.this, getString(R.string.VolleyError));
            }

            @Override
            public void onStatusFalse(UpdateInfoResponseWrapper response) {
                CommonUtils.alert(UpdateInfoActivity.this, response.getResponse().getMessage());

            }
        });
    }

    private void selectPicture() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(UpdateInfoActivity.this);
        builder.setTitle("Select Profile Photo From");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {
                    if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                        ActivityCompat.requestPermissions(UpdateInfoActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                    } else {
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        String fileName = "temp.jpg";
                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Images.Media.TITLE, fileName);
                        capturedImageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);
                        startActivityForResult(takePictureIntent, 2);
                    }
                } else if (options[item].equals("Choose from Gallery")) {
                    if ((ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) || (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                        ActivityCompat.requestPermissions(UpdateInfoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                    } else {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 3);
                    }
                }
            }
        });
        builder.show();

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    String fileName = "temp.jpg";
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, fileName);
                    capturedImageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, capturedImageUri);
                    startActivityForResult(takePictureIntent, 2);
                } else {
                    if (grantResults[0] < 0) {
                        Toast.makeText(UpdateInfoActivity.this, "Camera Permission Required", Toast.LENGTH_SHORT).show();
                    } else if (grantResults[1] < 0 || grantResults[2] < 0) {
                        Toast.makeText(UpdateInfoActivity.this, "Storage Permission Required", Toast.LENGTH_SHORT).show();
                    }
                }
                break;

            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 3);
                } else {
                    if (grantResults[0] < 0) {
                        Toast.makeText(UpdateInfoActivity.this, "Camera Permission Required", Toast.LENGTH_SHORT).show();
                    } else if (grantResults[0] < 0 || grantResults[1] < 0) {
                        Toast.makeText(UpdateInfoActivity.this, "Storage Permission Required", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                CropImage.activity(capturedImageUri)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(UpdateInfoActivity.this);

            }
        } else if (requestCode == 3) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                CropImage.activity(selectedImage)
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .setAspectRatio(1, 1)
                        .start(UpdateInfoActivity.this);
                Bitmap bitmap = null;

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(Uri.parse(String.valueOf(selectedImage)), projection, null, null, null);
                int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                cameraPicturePath = cursor.getString(column_index_data);
                profilePic.setImageBitmap(BitmapFactory.decodeFile(cameraPicturePath));
            }
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = null;

                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                path = MediaStore.Images.Media.insertImage(this.getContentResolver(), bitmap, "Title", null);
                String[] projection = {MediaStore.Images.Media.DATA};
                Cursor cursor = managedQuery(Uri.parse(path), projection, null, null, null);
                int column_index_data = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                cameraPicturePath = cursor.getString(column_index_data);
                profilePic.setImageBitmap(BitmapFactory.decodeFile(cameraPicturePath));

                InputStream inputStream = null;//You can get an inputStream using any IO API
                try {
                    inputStream = new FileInputStream(cameraPicturePath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                byte[] bytes1;
                byte[] buffer = new byte[8192];
                int bytesRead;
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                try {
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        output.write(buffer, 0, bytesRead);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                bytes1 = output.toByteArray();
                encodedString = Base64.encodeToString(bytes1, Base64.DEFAULT);
            }
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
