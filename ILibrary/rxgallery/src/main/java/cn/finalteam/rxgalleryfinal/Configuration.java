package cn.finalteam.rxgalleryfinal;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntRange;

import com.yalantis.ucrop.UCropActivity;
import com.yalantis.ucrop.model.AspectRatio;
import com.yalantis.ucrop.view.CropImageView;
import com.yalantis.ucrop.view.OverlayView;

import java.util.List;

import cn.finalteam.rxgalleryfinal.bean.MediaBean;
import cn.finalteam.rxgalleryfinal.imageloader.AbsImageLoader;
import cn.finalteam.rxgalleryfinal.imageloader.FrescoImageLoader;
import cn.finalteam.rxgalleryfinal.imageloader.GlideImageLoader;
import cn.finalteam.rxgalleryfinal.imageloader.PicassoImageLoader;
import cn.finalteam.rxgalleryfinal.imageloader.UniversalImageLoader;

/**
 * Desction:配置信息
 * Author:pengjianbo
 * Date:16/5/7 下午3:58
 *
 * @since 1.0.0
 */
public class Configuration implements Parcelable {
    protected Configuration() {
    }

    private Context context;
    /**
     * 设置显示的附件类型是图片还是视频
     */
    @Deprecated
    private boolean image = true;
    /**
     * 被选中的附件集合
     */
    private List<MediaBean> selectedList;
    /**
     * 判断是单选还是多选文件；true表示单选
     */
    private boolean radio;
    private boolean crop;
    private int maxSize = 1;

    private int imageLoaderType;
    private int imageConfig;
    private boolean hideCamera;

    public static final int VERSION_CODE_100 = 100;
    public static final int VERSION_CODE_LAST = 110;
    public static final int MEDIA_TYPE_IMAGE = 3001;
    public static final int MEDIA_TYPE_VIDEO = 3002;
    public static final int MEDIA_TYPE_IMAGE_AND_VIDEO = 3003;
    private int mediaType = MEDIA_TYPE_IMAGE_AND_VIDEO;
    private int versionCode = VERSION_CODE_LAST;

    //==========UCrop START==========
    //是否隐藏裁剪页面底部控制栏,默认显示
    private boolean hideBottomControls;
    //图片压缩质量,默认不压缩
    private int compressionQuality = 90;
    //手势方式,默认all
    private int[] gestures;
    //设置图片最大值,默认根据屏幕得出
    private int maxBitmapSize = CropImageView.DEFAULT_MAX_BITMAP_SIZE;
    //设置最大缩放值,默认10.f
    private float maxScaleMultiplier = CropImageView.DEFAULT_MAX_SCALE_MULTIPLIER;
    //宽高比
    private float aspectRatioX;
    private float aspectRatioY;
    //等比缩放默认值索引,默认原图比例
    private int selectedByDefault;
    //等比缩放值表,默认1:1,3:4,原图比例,3:2,16:9
    private AspectRatio[] aspectRatio;
    //是否允许改变裁剪大小
    private boolean freestyleCropEnabled = OverlayView.DEFAULT_FREESTYLE_CROP_ENABLED;
    //是否显示裁剪框半透明椭圆浮层
    private boolean ovalDimmedLayer = OverlayView.DEFAULT_OVAL_DIMMED_LAYER;
    private int maxResultWidth;
    private int maxResultHeight;

    //==========UCrop END==========

    protected Configuration(Parcel in) {
        image = in.readByte() != 0;
        mediaType = in.readInt();
        versionCode = in.readInt();
        selectedList = in.createTypedArrayList(MediaBean.CREATOR);
        radio = in.readByte() != 0;
        crop = in.readByte() != 0;
        maxSize = in.readInt();
        hideBottomControls = in.readByte() != 0;
        compressionQuality = in.readInt();
        gestures = in.createIntArray();
        maxBitmapSize = in.readInt();
        maxScaleMultiplier = in.readFloat();
        aspectRatioX = in.readFloat();
        aspectRatioY = in.readFloat();
        selectedByDefault = in.readInt();
        aspectRatio = in.createTypedArray(AspectRatio.CREATOR);
        freestyleCropEnabled = in.readByte() != 0;
        ovalDimmedLayer = in.readByte() != 0;
        maxResultWidth = in.readInt();
        maxResultHeight = in.readInt();
        imageLoaderType = in.readInt();
        imageConfig = in.readInt();
        hideCamera = in.readByte() != 0;
    }

    public static final Creator<Configuration> CREATOR = new Creator<Configuration>() {
        @Override
        public Configuration createFromParcel(Parcel in) {
            return new Configuration(in);
        }

        @Override
        public Configuration[] newArray(int size) {
            return new Configuration[size];
        }
    };

    /**
     * @deprecated Use {@link #getMediaType()} instead
     */
    @Deprecated
    public boolean isImage() {
        return image;
    }

    /**
     * @deprecated Use {@link #setMediaType(int)} instead
     */
    @Deprecated
    protected void setImage(boolean image) {
        this.image = image;
    }

    public Context getContext() {
        return context;
    }

    protected void setContext(Context context) {
        this.context = context;
    }

    public List<MediaBean> getSelectedList() {
        return selectedList;
    }

    protected void setSelectedList(List<MediaBean> selectedList) {
        this.selectedList = selectedList;
    }

    public boolean isRadio() {
        return radio;
    }

    protected void setRadio(boolean radio) {
        this.radio = radio;
    }

    public int getMaxSize() {
        return maxSize;
    }

    protected void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }

    public boolean isHideCamera() {
        return hideCamera;
    }

    public void setHideCamera(boolean hideCamera) {
        this.hideCamera = hideCamera;
    }

    public AbsImageLoader getImageLoader() {
        AbsImageLoader imageLoader = null;
        switch (imageLoaderType) {
            case 1:
                imageLoader = new PicassoImageLoader();
                break;
            case 2:
                imageLoader = new GlideImageLoader();
                break;
            case 3:
                imageLoader = new FrescoImageLoader();
                break;
            case 4:
                imageLoader = new UniversalImageLoader();
                break;
            case 5:

                break;
        }
        return imageLoader;
    }

    protected void setImageLoaderType(int imageLoaderType) {
        this.imageLoaderType = imageLoaderType;
    }

    public Bitmap.Config getImageConfig() {
        switch (imageConfig) {
            case 1:
                return Bitmap.Config.ALPHA_8;
            case 2:
                return Bitmap.Config.ARGB_4444;
            case 3:
                return Bitmap.Config.ARGB_8888;
            case 4:
                return Bitmap.Config.RGB_565;
        }
        return Bitmap.Config.ARGB_8888;
    }

    public void setImageConfig(int imageConfig) {
        this.imageConfig = imageConfig;
    }

    public boolean isHideBottomControls() {
        return hideBottomControls;
    }

    public void setHideBottomControls(boolean hideBottomControls) {
        this.hideBottomControls = hideBottomControls;
    }

    public int getCompressionQuality() {
        return compressionQuality;
    }

    public void setCompressionQuality(int compressionQuality) {
        this.compressionQuality = compressionQuality;
    }

    public void setAllowedGestures(@UCropActivity.GestureTypes int[] gestures) {
        this.gestures = gestures;
    }

    public int[] getAllowedGestures() {
        return gestures;
    }

    public int getMaxBitmapSize() {
        return maxBitmapSize;
    }

    public void setMaxBitmapSize(int maxBitmapSize) {
        this.maxBitmapSize = maxBitmapSize;
    }

    public float getMaxScaleMultiplier() {
        return maxScaleMultiplier;
    }

    public void setMaxScaleMultiplier(float maxScaleMultiplier) {
        this.maxScaleMultiplier = maxScaleMultiplier;
    }

    public float getAspectRatioX() {
        return aspectRatioX;
    }

    public void setAspectRatioX(float aspectRatioX) {
        this.aspectRatioX = aspectRatioX;
    }

    public float getAspectRatioY() {
        return aspectRatioY;
    }

    public void setAspectRatioY(float aspectRatioY) {
        this.aspectRatioY = aspectRatioY;
    }

    public void setAspectRatioOptions(int selectedByDefault, AspectRatio... aspectRatio) {
        this.selectedByDefault = selectedByDefault;
        this.aspectRatio = aspectRatio;
    }

    public int getSelectedByDefault() {
        return selectedByDefault;
    }

    public void setSelectedByDefault(int selectedByDefault) {
        this.selectedByDefault = selectedByDefault;
    }

    public AspectRatio[] getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(AspectRatio[] aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

    public boolean isFreestyleCropEnabled() {
        return freestyleCropEnabled;
    }

    public void setFreestyleCropEnabled(boolean freestyleCropEnabled) {
        this.freestyleCropEnabled = freestyleCropEnabled;
    }

    public boolean isOvalDimmedLayer() {
        return ovalDimmedLayer;
    }

    public void setOvalDimmedLayer(boolean ovalDimmedLayer) {
        this.ovalDimmedLayer = ovalDimmedLayer;
    }

    public boolean isCrop() {
        return crop;
    }

    public void setCrop(boolean crop) {
        this.crop = crop;
    }

    public void setMaxResultSize(@IntRange(from = 100) int width, @IntRange(from = 100) int height) {
        this.maxResultWidth = width;
        this.maxResultHeight = height;
    }

    public int getMaxResultHeight() {
        return maxResultHeight;
    }

    public int getMaxResultWidth() {
        return maxResultWidth;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (image ? 1 : 0));
        parcel.writeInt(mediaType);
        parcel.writeInt(versionCode);
        parcel.writeTypedList(selectedList);
        parcel.writeByte((byte) (radio ? 1 : 0));
        parcel.writeByte((byte) (crop ? 1 : 0));
        parcel.writeInt(maxSize);
        parcel.writeByte((byte) (hideBottomControls ? 1 : 0));
        parcel.writeInt(compressionQuality);
        parcel.writeIntArray(gestures);
        parcel.writeInt(maxBitmapSize);
        parcel.writeFloat(maxScaleMultiplier);
        parcel.writeFloat(aspectRatioX);
        parcel.writeFloat(aspectRatioY);
        parcel.writeInt(selectedByDefault);
        parcel.writeTypedArray(aspectRatio, i);
        parcel.writeByte((byte) (freestyleCropEnabled ? 1 : 0));
        parcel.writeByte((byte) (ovalDimmedLayer ? 1 : 0));
        parcel.writeInt(maxResultWidth);
        parcel.writeInt(maxResultHeight);
        parcel.writeInt(imageLoaderType);
        parcel.writeInt(imageConfig);
        parcel.writeByte((byte) (hideCamera ? 1 : 0));
    }

    /**
     * @since 1.1.0
     */
    public int getMediaType() {
        return mediaType;
    }

    /**
     * @since 1.1.0
     */
    public void setMediaType(int mediaType) {
        switch (mediaType) {
            case MEDIA_TYPE_IMAGE:
            case MEDIA_TYPE_VIDEO:
                setVersionCode(VERSION_CODE_100);
                break;
            case MEDIA_TYPE_IMAGE_AND_VIDEO:
                setVersionCode(VERSION_CODE_LAST);
                break;
            default:
                throw new IllegalArgumentException("the media type must in MEDIA_TYPE_IMAGE,MEDIA_TYPE_VIDEO,MEDIA_TYPE_IMAGE_AND_VIDEO");
        }
        this.mediaType = mediaType;
    }

    /**
     * @since 1.1.0
     */
    public int getVersionCode() {
        return versionCode;
    }

    /**
     * @since 1.1.0
     */
    public void setVersionCode(int versionCode) {
        switch (versionCode) {
            case VERSION_CODE_100:
            case VERSION_CODE_LAST:
                break;
            default:
                throw new IllegalArgumentException("the version code must in VERSION_CODE_100,VERSION_CODE_LAST");
        }
        this.versionCode = versionCode;
    }
}