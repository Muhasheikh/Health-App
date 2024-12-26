package com.example.finalprojectsunday;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Size;
import android.util.TypedValue;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.Preview;
import androidx.camera.extensions.HdrImageCaptureExtender;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import com.example.finalprojectsunday.env.BorderedText;
import com.example.finalprojectsunday.env.ImageUtils;
import com.example.finalprojectsunday.env.Logger;
import com.example.finalprojectsunday.tflite.Classifier;
import com.example.finalprojectsunday.tflite.Detector;
import com.example.finalprojectsunday.tflite.TFLiteObjectDetectionAPIModel;
import com.example.finalprojectsunday.tflite.TfLiteSegmentaion;
import com.example.finalprojectsunday.tracking.MultiBoxTracker;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class ModelActivity extends AppCompatActivity {

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private static final String CHANNEL_ID = "CHANNEL_ID";
    private static final String TAG = "main";
    private Runnable imageConverter;
    private final byte[] bytes = new byte[0];
    Bitmap bitmapview;
    public static int rice_show,egg_show,rotti_show,dhal_show,vegtable_show,meat_show;
    private final Queue<Integer> availableColors = new LinkedList<Integer>();
    private Executor executor = Executors.newSingleThreadExecutor();
    private int REQUEST_CODE_PERMISSIONS = 1001;
    private final String[] REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"};
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    PreviewView previewView;
    private ImageCapture imageCapture;
    protected int previewWidth = 0;
    protected int previewHeight = 0;
    private Bitmap rgbFrameBitmap = null;
    private Bitmap croppedBitmap = null;
    private Bitmap cropCopyBitmap = null;
    public Intent intent;
    private Matrix frameToCropTransform;
    private Matrix cropToFrameTransform;
    private byte[][] yuvBytes = new byte[3][];
    private int[] rgbBytes = null;
    private long timestamp = 0;
    private static Integer[] COLORS = makeColorGradient(.2f, .2f, .2f, 0, 2, 4, 21);
    PreviewView mPreviewView;
    ImageView image;
    ImageView resize;
    ImageView finalMasl;
    ImageView original_imageview;
    Button LivePrediction;
    ImageButton takePicture;
    Button MoreDetails;
    Uri uri;
    int CAMERA_PICTURE=1;
    private final Paint boxPaint = new Paint();

    // Configuration values for the prepackaged SSD model.
    private static final int TF_OD_API_INPUT_SIZE = 320;
    private static final boolean TF_OD_API_IS_QUANTIZED = false;
    private static final String TF_OD_API_MODEL_FILE = "model66.tflite";    // "detect.tflite";
    private static final String TF_OD_API_LABELS_FILE = "labelstfl.txt";///"model_det.txt";  //
    private static final DetectorMode MODE = DetectorMode.TF_OD_API;

    // Minimum detection confidence to track a detection.
    private static final float MINIMUM_CONFIDENCE_TF_OD_API = 0.5f;
    private static final boolean MAINTAIN_ASPECT = false;
    private static final Size DESIRED_PREVIEW_SIZE = new Size(640, 480);
    private static final boolean SAVE_PREVIEW_BITMAP = false;
    private static final float TEXT_SIZE_DIP = 10;


    private Paint paint = new Paint();


    private MultiBoxTracker tracker;

    ImageView trackingOverlay;




    private Detector detector;

    private Classifier classifier;


    private BorderedText borderedText;
    DatabaseReference databaseReference1;

    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
   // private MultiBoxTracker tracker;


    private static final Logger LOGGER = new Logger();


    ////////////////////////////////////////////////////////////////////////Second ML Variable Decleration///////////////////////////////////////////////

    private static final int TF_OD_API_INPUT_SIZE_SEG = 256;
    private static final boolean TF_OD_API_IS_QUANTIZED_SEG = false;
    private static final String TF_OD_API_MODEL_FILE_SEG = "portrait_video.tflite";
    private static final String TF_OD_API_LABELS_FILE_SEG = "file:///android_asset/deeplab_label_map.txt";
    private static final boolean MAINTAIN_ASPECT_SEG = false;
    private static final Size DESIRED_PREVIEW_SIZE_SEG = new Size(640, 480);
    private static final boolean SAVE_PREVIEW_BITMAP_SEG = false;
    private static final float TEXT_SIZE_DIP_SEG = 10;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tfe_is_activity_main);
        mPreviewView=findViewById(R.id.view_finder);
        image=findViewById(R.id.original_imageview);
        takePicture=findViewById(R.id.capture_button);
        original_imageview=findViewById(R.id.original_imageview);
        LivePrediction=findViewById(R.id.LivePrediction);
        MoreDetails=findViewById(R.id.MoreDetails);
        TextView show;







        LivePrediction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(new Intent(ModelActivity.this, DetectorActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));






            }
        });

        MoreDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getBaseContext(), MoreDetails.class);
                startActivity(intent);
            }
        });



        firebaseDatabase= FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Meal Plan");



        final float textSizePx =
                TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP, TEXT_SIZE_DIP, getResources().getDisplayMetrics());
        borderedText = new BorderedText(textSizePx);
        borderedText.setTypeface(Typeface.MONOSPACE);



        int cropSize = TF_OD_API_INPUT_SIZE;


        try {
            detector =
                    TFLiteObjectDetectionAPIModel.create(
                            this,
                            TF_OD_API_MODEL_FILE,
                            TF_OD_API_LABELS_FILE,
                            TF_OD_API_INPUT_SIZE,
                            TF_OD_API_IS_QUANTIZED);
            cropSize = TF_OD_API_INPUT_SIZE;
        } catch (final IOException e) {
            e.printStackTrace();
            LOGGER.e(e, "Exception initializing Detector!");
            Toast toast =
                    Toast.makeText(
                            getApplicationContext(), "Detector could not be initialized", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }





        if(allPermissionsGranted()){
            startCamera(); //start camera if permission has been granted by user
        } else{
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

    }


    private void startCamera() {

        final ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        cameraProviderFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {

                    ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                    bindPreview(cameraProvider);

                } catch (ExecutionException | InterruptedException e) {
                    // No errors need to be handled for this Future.
                    // This should never be reached.
                }
            }
        }, ContextCompat.getMainExecutor(this));
    }


//Check All the Permission
    private boolean allPermissionsGranted(){

        for(String permission : REQUIRED_PERMISSIONS){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera();
            } else {
                Toast.makeText(this, "Permissions not granted by the user.", Toast.LENGTH_SHORT).show();
                this.finish();
            }
        }
    }

//Image Capture
    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {

        Preview preview = new Preview.Builder()
                .build();



        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build();

        ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                .build();

        ImageCapture.Builder builder = new ImageCapture.Builder();

        //Vendor-Extensions (The CameraX extensions dependency in build.gradle)
        HdrImageCaptureExtender hdrImageCaptureExtender = HdrImageCaptureExtender.create(builder);

        // Query if extension is available (optional).
        if (hdrImageCaptureExtender.isExtensionAvailable(cameraSelector)) {
            // Enable the extension if available.
            hdrImageCaptureExtender.enableExtension(cameraSelector);
        }

        final ImageCapture imageCapture = builder
                .setTargetRotation(this.getWindowManager().getDefaultDisplay().getRotation())
                .build();

        preview.setSurfaceProvider(mPreviewView.createSurfaceProvider());

        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, preview, imageAnalysis, imageCapture);

        takePicture.setOnClickListener(v -> {

            SimpleDateFormat mDateFormat = new SimpleDateFormat("yyyyMMddHHmmss", Locale.US);
            File file = new File(getBatchDirectoryName(), mDateFormat.format(new Date())+ ".jpg");


            ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(file).build();
            imageCapture.takePicture(outputFileOptions, executor, new ImageCapture.OnImageSavedCallback () {
                @Override
                public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        private ContentResolver contentResolver;

                        @Override
                        public void run() {
                            Toast.makeText(ModelActivity.this, "Image Saved successfully", Toast.LENGTH_SHORT).show();
                            galleryAddPic(file, 0);

                            if (uri != null) {
                                Bitmap bitmap = null;
                                try {
                                    original_imageview.setImageURI(uri);
                                    View FinalMask10 = findViewById(R.id.result_imageview);
                                    ((ImageView) FinalMask10).setImageResource(android.R.color.transparent);

                                    Detection();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    });
                }
                @Override
                public void onError(@NonNull ImageCaptureException error) {

                    Toast.makeText(ModelActivity.this, "unsuccessfully", Toast.LENGTH_SHORT).show();
                    error.printStackTrace();
                }
            });
        });
    }


//Object Detection
    private void Detection() throws IOException {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println(dtf.format(now));
        databaseReference1=databaseReference.child(dtf.format(now));



        int Width=original_imageview.getWidth();
        int Height=original_imageview.getHeight();
        int cropSize = TF_OD_API_INPUT_SIZE;

        Bitmap bitmap =MediaStore.Images.Media.getBitmap(this.getContentResolver(),uri);
        bitmapview=bitmap;
        System.out.println(bitmap);

//
        Bitmap bitmap12=getResizedBitmap(bitmap,320,320);


        System.out.println("Cropped Image "+bitmap12.getWidth());

        View mImg = findViewById(R.id.result_imageview);



        Canvas cnvs=new Canvas(bitmap12);

        Paint paint = new Paint();
        paint.setAlpha(0xA0); // the transparency
        paint.setColor(Color.RED); // color is red
        paint.setStyle(Paint.Style.STROKE); // stroke or fill or ...
        paint.setStrokeWidth(1); // the stroke width

//
        frameToCropTransform =
                ImageUtils.getTransformationMatrix(
                        previewWidth, previewHeight,
                        cropSize, cropSize,
                        90, MAINTAIN_ASPECT);

        cropToFrameTransform = new Matrix();
        frameToCropTransform.invert(cropToFrameTransform);
        final List<Detector.Recognition> results = detector.recognizeImage(bitmap12);
        //
        final List<Detector.Recognition> mappedRecognitions =
                new ArrayList<Detector.Recognition>();

        int val=0;////
        int count_cal=0;
        int count_cal_prev=0;
        vegtable_show=0;
        rice_show=0;
        meat_show=0;
        dhal_show=0;
        egg_show=0;
        rotti_show=0;




try {


    for (final Detector.Recognition result : results) {
        final RectF location = result.getLocation();
        if (location != null && result.getConfidence() >= 0.1f) {
            System.out.println(result.getLocation().bottom);
            float top = (float) result.getLocation().top;
            float left = (float) result.getLocation().left;
            float bottom = (float) result.getLocation().bottom;
            float right = (float) result.getLocation().right;
            val = val + 1;
            Bitmap bitmap13 = bitmap12;
            cnvs.drawBitmap(bitmap13, 0, 0, null);
            cnvs.drawRect(left, top, right, bottom, paint);
            ((ImageView) mImg).setImageBitmap(bitmap13);
            int Crop_width = Math.round(right) - Math.round(left);
            int Crop_height = Math.round(bottom) - Math.round(top);
            Matrix matrix = new Matrix();
            matrix.postRotate(-90);
            Bitmap imagemCortada = Bitmap.createBitmap(bitmap13, Math.round(left), Math.round(top), Crop_width, Crop_height, matrix, true);
            int Area = Crop_width * Crop_height;
            int count = Segementation(imagemCortada, val);
            System.out.println("count of indicidual" + count);
            count_cal = Calculate_Callories(result.getTitle(), count, Area);
            count_cal_prev = count_cal + count_cal_prev;

        }
    }
}
catch (Exception e){

}
        Notify.build(getApplicationContext())
                .setTitle("Meal Plan")
                .setContent("Your Meal Plan total Calories Count"+count_cal_prev+"Kj")
                .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                .setLargeIcon(R.drawable.traineer)
                .largeCircularIcon()
                .setColor(R.color.darkTextColor)
                .show();

        data obj=new data("Total Meal",count_cal_prev);
        databaseReference1.child("Total Meal").setValue(obj);



    }

    private int Calculate_Callories(String title, int count, int Area) {
        float pixel_count1=(float)Area/65536;
        float pixel_count=(float)pixel_count1*(65536-count);
        int nut=Math.round(pixel_count);

        if(title.equals("vegi")){

            vegtable_show=nut+vegtable_show;
            data obj=new data("vegitable",nut);
            databaseReference1.child("vegetable").setValue(obj);

        }
        if(title.equals("rice")){

            rice_show=nut+rice_show;
            data obj=new data("rice",nut);
            databaseReference1.child("rice").setValue(obj);

        }
        if(title.equals("meat")){
            meat_show=nut+meat_show;
            data obj=new data("meat",nut);
            databaseReference1.child("meat").setValue(obj);

        }

        if(title.equals("dhal")){
            dhal_show=nut+dhal_show;
            data obj=new data("dhal",nut);
            databaseReference1.child("dhal").setValue(obj);

        }
        if(title.equals("egg")){
            egg_show=nut+egg_show;
            data obj=new data("egg",nut);
            databaseReference1.child("egg").setValue(obj);

        }
        if(title.equals("roti")){
            rotti_show=nut+rotti_show;
            data obj=new data("rotti",nut);
            databaseReference1.child("rotti").setValue(obj);

        }


    return nut;
    }
    private int Segementation(Bitmap imagemCortada12, int value) {

        ++timestamp;
        final long currTimestamp = timestamp;

        try {
            classifier =
                    TfLiteSegmentaion.create(
                            getAssets(),
                            TF_OD_API_MODEL_FILE_SEG,
                            TF_OD_API_LABELS_FILE_SEG,
                            TF_OD_API_INPUT_SIZE_SEG,
                            TF_OD_API_IS_QUANTIZED_SEG
                    );
           int cropSize_SEG = TF_OD_API_INPUT_SIZE_SEG;
        } catch (final IOException e) {
            e.printStackTrace();
            LOGGER.e(e, "Exception initializing classifier!");
            Toast toast =
                    Toast.makeText(
                            getApplicationContext(), "Classifier could not be initialized", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }


        Bitmap bitmap12_seg=getResizedBitmap(imagemCortada12,256,256);////
//
//
        final float[][][][] results = classifier.recognizeImage(bitmap12_seg);
        System.out.println(results);
        for (final int color : COLORS) {
            availableColors.add(color);
        }
//
        int w=300;
        int h=300;
        int pos = 0;
        float xw = w / 256f;
        float xh = h / 256f;
        RectF r = new RectF();
        int ty=0;

        final Canvas canvas = null;

        int color = Color.BLUE;

        for (int y = 0; y < 256; y++) {
            for (int x = 0; x < 256; x++) {
               pos = getIndexOfMax(results[0][y][x]);
                if (pos > 0) {
                    ty=ty+1;


                    bitmap12_seg.setPixel(x,y,color);

                }


            }
        }
   return ty;

    }



    public int getIndexOfMax(float array[]) {
        if (array.length == 0) {
            return -1; // array contains no elements
        }
        float max = array[0];
        int pos = 0;

        for (int i = 1; i < array.length; i++) {
            if (max < array[i]) {
                pos = i;
                max = array[i];
            }
        }
        return pos;
    }
    //Resixe the Image to 300*300
    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

//Gallerry Location
    public String getBatchDirectoryName() {
        String app_folder_path;
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            app_folder_path = getExternalFilesDir(Environment.DIRECTORY_PICTURES).toString();
        } else {
            app_folder_path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).toString() + "/Camera";
        }

        File dir = new File(app_folder_path);
        if (!dir.exists() && !dir.mkdirs()) {
        }
        return app_folder_path;
    }



//last Image Add to Gallery
    private void galleryAddPic(File originalFile, int mediaType) {
        if (!originalFile.exists()) {
            return;
        }

        int pathSeparator = String.valueOf(originalFile).lastIndexOf('/');
        int extensionSeparator = String.valueOf(originalFile).lastIndexOf('.');
        String filename = pathSeparator >= 0 ? String.valueOf(originalFile).substring(pathSeparator + 1) : String.valueOf(originalFile);
        String extension = extensionSeparator >= 0 ? String.valueOf(originalFile).substring(extensionSeparator + 1) : "";

        // Credit: https://stackoverflow.com/a/31691791/2373034
        String mimeType = extension.length() > 0 ? MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension.toLowerCase(Locale.ENGLISH)) : null;

        ContentValues values = new ContentValues();
        values.put(MediaStore.MediaColumns.TITLE, filename);
        values.put(MediaStore.MediaColumns.DISPLAY_NAME, filename);
        values.put(MediaStore.MediaColumns.DATE_ADDED, System.currentTimeMillis() / 1000);

        if (mimeType != null && mimeType.length() > 0)
            values.put(MediaStore.MediaColumns.MIME_TYPE, mimeType);

        Uri externalContentUri;
        if (mediaType == 0)
            externalContentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        else if (mediaType == 1)
            externalContentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        else
            externalContentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        // Android 10 restricts our access to the raw filesystem, use MediaStore to save media in that case
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/Camera");
            values.put(MediaStore.MediaColumns.DATE_TAKEN, System.currentTimeMillis());
            values.put(MediaStore.MediaColumns.IS_PENDING, true);

            uri = getContentResolver().insert(externalContentUri, values);

            if (uri != null) {
                try {
                    if (WriteFileToStream(originalFile, getContentResolver().openOutputStream(uri))) {
                        values.put(MediaStore.MediaColumns.IS_PENDING, false);
                        getContentResolver().update(uri, values, null, null);
                    }
                } catch (Exception e) {
                    getContentResolver().delete(uri, null, null);
                }
            }
            originalFile.delete();
        } else {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(Uri.fromFile(originalFile));
            sendBroadcast(mediaScanIntent);
        }



    } //gallery add end

//Write to the Gallery
    private static boolean WriteFileToStream(File file, OutputStream out){
        try
        {
            InputStream in = new FileInputStream( file );
            try
            {
                byte[] buf = new byte[1024];
                int len;
                while( ( len = in.read( buf ) ) > 0 )
                    out.write( buf, 0, len );
            }
            finally
            {
                try
                {
                    in.close();
                }
                catch( Exception e )
                {
                    //Log.e( "Unity", "Exception:", e );
                }
            }
        }
        catch( Exception e )
        {
            //Log.e( "Unity", "Exception:", e );
            return false;
        }
        finally
        {
            try
            {
                out.close();
            }
            catch( Exception e )
            {
                //Log.e( "Unity", "Exception:", e );
            }
        }
        return true;
    } //write end

    protected Size getDesiredPreviewFrameSize() {
        return DESIRED_PREVIEW_SIZE;
    }
// Which detection model to use: by default uses Tensorflow Object Detection API frozen
// checkpoints.
    private enum DetectorMode {
        TF_OD_API;
    }


    public static Integer[] makeColorGradient(float frequency1, float frequency2, float frequency3, float phase1, float phase2, float phase3, int len) {
        Integer[] c = new Integer[len];
        int center = 128;
        int width = 127;
        for (int i = 0; i < len; ++i) {
            int red = (int) (Math.sin(frequency1 * i + phase1) * width + center);
            int grn = (int) (Math.sin(frequency2 * i + phase2) * width + center);
            int blu = (int) (Math.sin(frequency3 * i + phase3) * width + center);
            c[i] = Color.rgb(red, grn, blu);
        }
        List<Integer> list = Arrays.asList(c);
        Collections.shuffle(list);

        return list.toArray(new Integer[]{});
    }








}