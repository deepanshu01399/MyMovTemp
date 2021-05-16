package com.deepanshu.mymovieapp.ui.activity.ui.composeEmail;


import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.deepanshu.mymovieapp.R;
import com.deepanshu.mymovieapp.adapter.SetImageAdapter;
import com.deepanshu.mymovieapp.mvvm.ComposeViewModel;
import com.deepanshu.mymovieapp.prefs.SharedPreferencesFactory;
import com.deepanshu.mymovieapp.ui.activity.BaseActivity;

import com.deepanshu.mymovieapp.ui.module.UploadedImage;
import com.deepanshu.mymovieapp.util.FileUtils;
import com.deepanshu.mymovieapp.util.PrefUtil;
import com.deepanshu.mymovieapp.util.StaticUtil;
import com.deepanshu.retrofit.errors.ApiError;
import com.deepanshu.retrofit.modules.reqestModule.compose_email.ComposeEmailRequest;
import com.deepanshu.retrofit.modules.reqestModule.compose_email.ComposeEmailRequestObject;
import com.deepanshu.retrofit.modules.reqestModule.compose_email.EmailFrom;
import com.deepanshu.retrofit.modules.reqestModule.compose_email.EmailText;
import com.deepanshu.retrofit.modules.reqestModule.compose_email.EmailTo;
import com.deepanshu.retrofit.modules.reqestModule.compose_email.EmailUser;
import com.deepanshu.retrofit.modules.responseModule.compose_email.ComposeEmailResponse;
import com.deepanshu.retrofit.modules.responseModule.compose_email.EmailModule;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.chip.ChipGroup;
import com.google.gson.Gson;
import com.htmleditor.HtmlTextEditor;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.deepanshu.mymovieapp.ui.fragment.BaseFragment.hideSoftKeyboard;
import static com.deepanshu.mymovieapp.util.AppUtil.SUCESS_RESPONSE;
import static com.deepanshu.mymovieapp.util.StaticUtil.base64String;
import static com.deepanshu.mymovieapp.util.StaticUtil.showSettingsDialog;

public class ComposeEmailActivity extends BaseActivity implements View.OnClickListener {
    LinearLayout mainLayout, chipsLine, chipsLineCC;
    private int CAMERA = 2, GALLERY = 11;
    Bitmap photo = null;
    private ProgressBar progressBar;
    String currentPhotoPath = "";
    private ArrayList<UploadedImage> uploadedImageArrayList = new ArrayList();
    Intent intent = null;
    private Uri fileUri = null;
    File file;
    List<MultipartBody.Part>  mulitpartAttach = null;
    private TextView backText;
    private static final int ASK_MULTIPLE_PERMISSION_REQUEST_CODE = 0;
    private String callFrom, callLogId = null;
    private static final int FILE_SELECT_CODE = 0;
    private LinearLayout emailAttachmentLay;
    private static String TAG = "ComposeEmailFrag";
    private TextView imgProfilePic;
    private final int COLUMN_COUNT = 3;
    private TextView txtUserName, txtuserOutBoundEmail, txtAttachement;
    private HashMap<String, RequestBody> mapnull = new HashMap<>();
    private String callItemId, callFromName, callingModule, callingEmailAddress;
    private EditText edtEmailTo, edtCC;
    private static final int REQUEST_CODE_SEARCH_RELATED_TO = 11;
    private ImageButton btncancelTo, btnAddotherToEmail, btnAddotherCCEmail;
    private ScrollView nestedScrollView;
    private static final int REQUEST_CODE_SEARCH_EMAIL_TEMPLATE = 12;
    private static final int REQUEST_CODE_SEARCH_EMAIL_TO = 13;
    private static final String EMAIL_FIELD = "email1";
    private ComposeViewModel composeViewModel;
    private EditText edtMailSubject;
    private HtmlTextEditor html_editor;
    private LinearLayout llhtmlEditor;
    private WebView simpleWebView;
    private String simpleWebViewText;
    private ChipGroup chipGroupTypeOfEmailTO, chipGroupTypeOfEmailCC;
    private List<String> chipEmailToDataList = new ArrayList<>();
    private ImageView imgBtnDocuments, composeEmailBtn, imgBtnDlt;

    private List<String> chipEmailCCDataList = new ArrayList<>();
    private List<String> chipEmailBCCDataList = new ArrayList<>();

    private List<EmailTo> emaillistTo;
    private List<MultipartBody.Part> multipartsList;
    private RecyclerView recylermageView;
    private SetImageAdapter setImageAdapter;
    private String firstName, lastname, userId, emailId;
    private RequestBody composeEmailRequestBody = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public ComposeEmailActivity(){

    }

    @Override
    public String getHeaderTitle() {
        return "Compose Email";
    }

    @Override
    public int getLayoutByID() {
        return R.layout.fragment_compose_email;
    }

    @Override
    public void getViewById() {
        SharedPreferencesFactory sharedPreferencesFactory = SharedPreferencesFactory.getInstance(ComposeEmailActivity.this);
        SharedPreferences prefs = sharedPreferencesFactory.getSharedPreferences(MODE_PRIVATE);
        firstName = sharedPreferencesFactory.getPreferenceValue(PrefUtil.PREFS_FIRST_NAME);
        lastname = sharedPreferencesFactory.getPreferenceValue(PrefUtil.PREFS_LAST_NAME);
        emailId = sharedPreferencesFactory.getPreferenceValue(PrefUtil.PREFS_EMAIL_ADDR);
        userId = sharedPreferencesFactory.getPreferenceValue(PrefUtil.PREFS_ID);

        emailAttachmentLay = findViewById(R.id.emailAttachmentLay);
        imgProfilePic = findViewById(R.id.imgProfilePic);
        txtAttachement = findViewById(R.id.txtAttachement);
        txtUserName = findViewById(R.id.txtUserName);
        txtuserOutBoundEmail = findViewById(R.id.txtuserOutBoundEmail);
        progressBar = findViewById(R.id.spin_kit);
        edtMailSubject = findViewById(R.id.edtMailSubject);
        edtMailSubject.setEnabled(true);
        html_editor = findViewById(R.id.html_editor);
        llhtmlEditor = findViewById(R.id.llhtmlEditor);
        simpleWebView = findViewById(R.id.simpleWebView);
        simpleWebView.setVisibility(View.GONE);
        edtEmailTo = findViewById(R.id.edtEmailTo);
        edtCC = findViewById(R.id.edtCC);
        btncancelTo = findViewById(R.id.btncancelTo);
        btnAddotherCCEmail = findViewById(R.id.btnAddotherCCEmail);
        btnAddotherToEmail = findViewById(R.id.btnAddotherToEmail);
        nestedScrollView = findViewById(R.id.nestedScrollView);
        chipGroupTypeOfEmailTO = findViewById(R.id.chipGroupTypeOfEmailTO);
        chipGroupTypeOfEmailCC = findViewById(R.id.chipGroupTypeOfEmailCC);
        recylermageView = findViewById(R.id.recylerviewImageView);

        chipsLine = findViewById(R.id.chipsLine);
        chipsLineCC = findViewById(R.id.chipsLineCC);
        btncancelTo.setVisibility(View.GONE);
        updateUserNameIcon(txtuserOutBoundEmail.getText().toString());
        LinearLayoutManager gridLayoutManager = new GridLayoutManager(this, COLUMN_COUNT);
        recylermageView.setLayoutManager(gridLayoutManager);
        setImageAdapter = new SetImageAdapter<>(this);
        recylermageView.setAdapter(setImageAdapter);
        //composeViewModel = new ViewModelProvider(this).get(ComposeViewModel.class);
        composeViewModel = new ViewModelProvider(this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(ComposeViewModel.class);
        setRecylerViewCallBack();

        hitcomposeEmailApi();

        nestedScrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Log.v("PARENT", "PARENT TOUCH");
                findViewById(R.id.simpleWebView).getParent()
                        .requestDisallowInterceptTouchEvent(false);
                return false;
            }

        });

        simpleWebView.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                Log.v("CHILD", "CHILD TOUCH");
                // Disallow the touch request for parent scroll on touch of
                // child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });


        txtuserOutBoundEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                updateUserNameIcon(txtuserOutBoundEmail.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        edtCC.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null && charSequence.length() > 0) {
                    int num = charSequence.charAt(0);
                    if (!((num >= 65 && num <= 90) || (num >= 97 && num <= 122))) {
                        CharSequence t = charSequence.subSequence(1, charSequence.length());
                        edtCC.setText(StaticUtil.removeChar(t.toString()));
                    }
                    //(".".equalsIgnoreCase(String.valueOf(charSequence.charAt(0))))
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtEmailTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null && charSequence.length() > 0) {
                    int num = charSequence.charAt(0);
                    if (!((num >= 65 && num <= 90) || (num >= 97 && num <= 122))) {
                        CharSequence t = charSequence.subSequence(1, charSequence.length());
                        edtEmailTo.setText(StaticUtil.removeChar(t.toString()));
                    }
                }
                if (edtEmailTo.getText().length() == 0) {
                    btncancelTo.setVisibility(View.GONE);
                } else {
                    btncancelTo.setVisibility(View.VISIBLE);

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        initClickListner();
        getTemplateData();

    }

    private void hitcomposeEmailApi() {
        composeViewModel.hitComposeEmailApi(getDeviceToken(this), composeEmailRequestBody, mulitpartAttach, false).observe(this, new Observer<ComposeEmailResponse>() {
            @Override
            public void onChanged(ComposeEmailResponse composeEmailResponse) {
                if(composeEmailResponse!=null){
                    Toast.makeText(ComposeEmailActivity.this,composeEmailResponse.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setRecylerViewCallBack() {
        setImageAdapter.setCallback(R.layout.email_doc_item, new SetImageAdapter.IClickable() {
            @Override
            public void init(View view, Object object) {

            }

            @Override
            public void execute(View view, Object object, final int position) {
                //TODO TYPECAST the object and enjoy :)
                UploadedImage uploadedImage = (UploadedImage) object;
                Gson gson = new Gson();
                Log.e(TAG, "image Uri:" + gson.toJson(uploadedImage));
                if (uploadedImage.getRequestCode() == FILE_SELECT_CODE)//if file is a document
                    ((ImageView) view.findViewById(R.id.imgDocUpload)).setImageDrawable(getResources().getDrawable(R.drawable.document));
                else
                    ((ImageView) view.findViewById(R.id.imgDocUpload)).setImageURI(uploadedImage.getUri());

                ((ImageButton) view.findViewById(R.id.btnCancel)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        uploadedImageArrayList.remove(position);
                        setImageAdapter.notifyDataSetChanged();
                        if (uploadedImageArrayList.size() > 0)
                            txtAttachement.setVisibility(View.VISIBLE);
                        else
                            txtAttachement.setVisibility(View.GONE);
                    }
                });

            }

            @Override
            public void onClick(View view, Object Object) {

            }

        });
    }


    private void initClickListner() {
        btncancelTo.setOnClickListener(this);
        btnAddotherToEmail.setOnClickListener(this);
        btnAddotherCCEmail.setOnClickListener(this);


    }


    @Override
    public void hideToolBarnextValue() {

    }

    @Override
    public void updateToolBarNextValue(String nextValue) {

    }

    @Override
    public void updateToolBarBackValue(String backTxtValue) {

    }

    @Override
    public void hadleToolBarNextValue(TextView textView) {

    }

    @Override
    public void handleToolBarBackValue(TextView textView) {

    }

    @Override
    public void onNetworkChangeStatus(boolean networkStatus, String msg) {

    }

    @Override
    public void showProgressBar() {

    }


    @Override
    public void hideProgressbar() {

    }

    @Override
    public void hideToolbarNext() {

    }

    @Override
    public void showToolbarNext() {

    }

    @Override
    public void manageToolBar() {
        View app_bar = findViewById(R.id.app_bar_toolbar);
        View toolbar = app_bar.findViewById(R.id.includeToolbar);
        //setSupportActionBar(toolbar);
        backText = toolbar.findViewById(R.id.composeEmail_Back_text);
        imgBtnDocuments = toolbar.findViewById(R.id.imgBtnDocuments);
        imgBtnDlt = toolbar.findViewById(R.id.imgBtnDlt);
        composeEmailBtn = toolbar.findViewById(R.id.compose_email_sendIcon);
        backText.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        backText.setOnClickListener(this);
        imgBtnDocuments.setOnClickListener(this);
        composeEmailBtn.setOnClickListener(this);


    }

    @Override
    public void onNetworkChange(boolean networkStatus, String msg) {

    }


    public void getComposeEmailDetails() {
        List<EmailTo> getEmailTOListIds = getEmailTOListIds();
        List<String> getEmailCCListIds = getEmailCCListIds();
        List<String> getEmailBCCListIDs = getEmailBCCListIds();
        EmailModule getEmailModule = getEmailModule();
        EmailFrom getEmailFrom = getEmailFrom();
        EmailUser getEmailUser = getEmailUser();
        EmailText getEmailText = getEmailText();
        ComposeEmailRequestObject composeEmailRequestObject = new ComposeEmailRequestObject();
        composeEmailRequestObject.setEmailUser(getEmailUser);
        composeEmailRequestObject.setEmailModule(getEmailModule);
        composeEmailRequestObject.setEmailFrom(getEmailFrom);
        composeEmailRequestObject.setEmailToList(getEmailTOListIds);
        composeEmailRequestObject.setEmailCcList(getEmailCCListIds);
        composeEmailRequestObject.setEmailBccList(getEmailBCCListIDs);
        composeEmailRequestObject.setEmailText(getEmailText);

        final ComposeEmailRequest composeEmailRequest = new ComposeEmailRequest();
        composeEmailRequest.setEmailAddressRequestObject(composeEmailRequestObject);

        Gson gson = new Gson();
        Log.e(TAG, "ComposeEmail json formate " + gson.toJson(composeEmailRequest)+", token:"+getDeviceToken(this));
        composeEmailRequestBody = RequestBody.create(MediaType.parse("text/plain"), gson.toJson(composeEmailRequest));

        List<MultipartBody.Part> attachmentList = getMultipartsList();
        //Toast.makeText(this,"token"+getDeviceToken(this),Toast.LENGTH_LONG).show();
       composeViewModel.hitComposeEmailApi(getDeviceToken(this), composeEmailRequestBody, attachmentList, true);

    }

    private EmailText getEmailText() {
        EmailText emailText = new EmailText(edtMailSubject.getText().toString(), base64String(html_editor.getText().toString()));
        return emailText;
    }

    private EmailUser getEmailUser() {
        EmailUser emailUser = new EmailUser();
        emailUser.setEmailUserId(userId);
        emailUser.setToName(firstName + " " + lastname);
        emailUser.setToEmail(emailId);
        return emailUser;
    }

    private EmailFrom getEmailFrom() {
        EmailFrom emailFrom = new EmailFrom("deepanshu@novoinvent.com","be140b02-a13a-9755-589d-607d6520a291");
        return emailFrom;
    }

    private EmailModule getEmailModule() {
        EmailModule emailModule = new EmailModule();
        emailModule.setId("");
        emailModule.setType("");
        return emailModule;

    }

    private List<String> getEmailCCListIds() {
        if(edtCC.getText()!=null && !edtCC.getText().toString().equalsIgnoreCase("")){
            Boolean validEMail = android.util.Patterns.EMAIL_ADDRESS.matcher(edtCC.getText().toString().trim()).matches();
            if (!validEMail) {
                edtCC.requestFocus();
                showSnackBarMessage(edtCC.getText().toString() + getString(R.string.is_not_a_valied_email));
            }else {
                String emailto = edtEmailTo.getText().toString();
                if (!chipEmailCCDataList.contains(emailto)) {
                        chipEmailCCDataList.add(edtEmailTo.getText().toString());
                }
            }
        }
        return chipEmailCCDataList;
    }
    private List<String> getEmailBCCListIds() {
        return chipEmailBCCDataList;
    }


    private List<EmailTo> getEmailTOListIds() {
        emaillistTo =  new ArrayList<>();
        String emailto = "";
        if(edtEmailTo.getText()!=null && !edtEmailTo.getText().toString().equalsIgnoreCase("")){
            Boolean validEMail = android.util.Patterns.EMAIL_ADDRESS.matcher(edtEmailTo.getText().toString().trim()).matches();
            if (!validEMail) {
                edtEmailTo.requestFocus();
                showSnackBarMessage(edtEmailTo.getText().toString() + getString(R.string.is_not_a_valied_email));
            }else{
             emailto =  edtEmailTo.getText().toString();
              if(!emaillistTo.contains(emailto)) {
                  String[] emailtoarray =  emailto.split("@");
                  EmailTo emailTo = new EmailTo(emailtoarray[0], emailto);
                  emaillistTo.add(emailTo);
              }
            }
        }
        for (int i = 0; i < chipEmailToDataList.size(); i++) {
            String chipEmailTo = chipEmailToDataList.get(i);
            String[] chipEmailTOarray = chipEmailTo.split("@");
            EmailTo emailTo = new EmailTo(chipEmailTOarray[0],chipEmailTo);
            emaillistTo.add(emailTo);
        }
        return emaillistTo;
    }

    private void updateUserNameIcon(String emailId) {
        if (emailId != null && !emailId.equalsIgnoreCase("")) {
            char firstChar = emailId.charAt(0);
            imgProfilePic.setText("" + Character.toUpperCase(firstChar) + "");
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btncancelTo:
                hideSoftKeyboard(this);
                if (edtEmailTo.getText().length() > 0) {
                    edtEmailTo.setText("");
                }

                break;

            case R.id.btnAddotherToEmail:
                setDataAndCreateEmailCHip(edtEmailTo, chipGroupTypeOfEmailTO, chipEmailToDataList);

                break;
            case R.id.btnAddotherCCEmail:
                setDataAndCreateEmailCHip(edtCC, chipGroupTypeOfEmailCC, chipEmailCCDataList);

                break;

            case R.id.composeEmail_Back_text:
                finish();
                break;
            case R.id.imgBtnDocuments:
                addAttachment();
                break;
            case R.id.compose_email_sendIcon:
                hideSoftInputKeypad();
                getComposeEmailDetails();
                break;

        }

    }

    private void addAttachment() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                    (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
                showPictureDialog();
            } else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, ASK_MULTIPLE_PERMISSION_REQUEST_CODE);

            }
        } else {
            showPictureDialog();

        }

    }

    public void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(new ContextThemeWrapper(this, R.style.Messagedialog));
        pictureDialog.setTitle("Select Option");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera",
                "Upload document "};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                Boolean isDeviceSupportcamer = isDeviceSupportCamera(ComposeEmailActivity.this);
                                if (isDeviceSupportcamer) {
                                    takePhotoFromCamera();
                                } else {
                                    Toast.makeText(ComposeEmailActivity.this,
                                            "Sorry! Your device doesn't support camera",
                                            Toast.LENGTH_LONG).show();
                                }
                                break;
                            case 2:
                                uploadPDFDocument();
                                break;
                        }
                    }
                });
        pictureDialog.show();

    }

    private void uploadPDFDocument() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //intent.setType("application/pdf");
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.", +Toast.LENGTH_SHORT).show();
        }
    }

    private void takePhotoFromCamera() {

        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // Ensure that there's a camera activity to handle the intent
                //if permission granted:
                if (takePictureIntent.resolveActivity(getApplicationContext().getPackageManager()) != null) {
                    File photfile = null;
                    photfile = createImageFile();
                    if (photfile != null) {
                        Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), "com.deepanshu.mymovieapp", photfile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, CAMERA);
                    }
                }
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                if (response.isPermanentlyDenied()) {
                    showSettingsDialog(ComposeEmailActivity.this, ComposeEmailActivity.this);
                }
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();

            }
        });


    }

    private File createImageFile() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp;
        File storageDir = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

        startActivityForResult(galleryIntent, GALLERY);
    }

    public static boolean isDeviceSupportCamera(Context context) {
        // this device has a camera
        // no camera on this device
        return context.getPackageManager().hasSystemFeature(
                PackageManager.FEATURE_CAMERA);
    }


    private void setDataAndCreateEmailCHip(EditText editText, ChipGroup chipGroup, List<String> emaillist) {
        hideSoftKeyboard(this);
        if (TextUtils.isEmpty(editText.getText())) {
            showSnackBarMessage(getResources().getString(R.string.email_have_no_name)); //string added by Deepak kumar
            return;
        } else if (editText.getText().toString().trim().length() > 0) {
            Boolean validEMail = android.util.Patterns.EMAIL_ADDRESS.matcher(editText.getText().toString().trim()).matches();
            if (!validEMail) {
                editText.requestFocus();
                showSnackBarMessage(editText.getText().toString() + getString(R.string.is_not_a_valied_email));
                return;
            }
        }
        if (emaillist.contains(editText.getText().toString())) {
            showSnackBarMessage(editText.getText().toString() + " " + getString(R.string.is_already_added));
            return;
        }

        final String emailID = editText.getText().toString();
        final Chip emailChip = createEmailchip(emailID);
        emailChip.setOnCloseIconClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chipGroup.removeView(emailChip);
                if (emaillist.contains(editText.getText())) {
                    emaillist.remove(editText.getText());
                }
            }
        });
        chipGroup.addView(emailChip);
        emaillist.add(editText.getText().toString());
        editText.setText("");


    }

    private void getTemplateData() {

    }

    private Chip createEmailchip(String emailId) {
        androidx.appcompat.view.ContextThemeWrapper newContext = new androidx.appcompat.view.ContextThemeWrapper(this, R.style.myChipStyle);
        final Chip chip = new Chip(newContext);
        chip.setTag(emailId);
        chip.setElevation(5f);
        ChipDrawable chipDrawable = (ChipDrawable) chip.getChipDrawable();
        //  chipDrawable.setShadowColor(getResources().getColor(R.color.shimmer_background));
        chipDrawable.setChipBackgroundColorResource(R.color.white);

        chip.setTextColor(getResources().getColor(R.color.text_grey));

        int paddingDp = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, 10,
                getResources().getDisplayMetrics()
        );
        chip.setPadding(paddingDp, paddingDp, paddingDp, paddingDp);
        chip.setText(emailId);
        chip.setTypeface(ResourcesCompat.getFont(this, R.font.poppins_medium));
        chip.setBackgroundColor(getResources().getColor(R.color.white));
        chip.setCloseIconEnabled(true);
        return chip;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ASK_MULTIPLE_PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            showPictureDialog();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY || requestCode == FILE_SELECT_CODE) {
                Uri uri = null;
                if (data.getClipData() != null) {
                    for (int index = 0; index < data.getClipData().getItemCount(); index++) {
                        uri = data.getClipData().getItemAt(index).getUri();
                        Log.e("filesUri [" + uri + "] : ", String.valueOf(uri));
                        uploadFile(uri, requestCode);
                    }
                } else {
                    uri = data.getData();
                    uploadFile(uri, requestCode);
                }

            }
            if (requestCode == CAMERA) {

            }

        }
    }

    private void uploadFile(Uri uri, int requestCode) {
        //file = new File(new FileUtils(getApplicationContext()).getPath(uri));
        UploadedImage uploadedImage = new UploadedImage();
        uploadedImage.setUri(uri);
        uploadedImage.setRequestCode(requestCode);
        uploadedImageArrayList.add(uploadedImage);
        setImageAdapter.setData(uploadedImageArrayList, false);
        if (uploadedImageArrayList.size() > 0)
            txtAttachement.setVisibility(View.VISIBLE);
        else
            txtAttachement.setVisibility(View.GONE);

    }

    public List<MultipartBody.Part> getMultipartsList() {
        List<MultipartBody.Part> uriArrayList = null;
        if (uploadedImageArrayList != null && uploadedImageArrayList.size() > 0) {
            uriArrayList = new ArrayList<>();
            for (int i = 0; i < uploadedImageArrayList.size(); i++) {
                uriArrayList.add(prepareFilePart("email_attachment[" + i + "]", uploadedImageArrayList.get(i).getUri()));
            }

        }
        return uriArrayList;
    }

    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // use the FileUtils to get the actual file by uri
        File file = new File(new FileUtils(getApplicationContext()).getPath(fileUri));
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("*/*"), file);
        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);

    }
    
}