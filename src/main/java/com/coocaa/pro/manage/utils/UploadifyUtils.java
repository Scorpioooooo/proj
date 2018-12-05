package com.coocaa.pro.manage.utils;

import com.coocaa.fire.utils.ImageUtil;
import com.coocaa.fire.utils.PropertiesUtil;
import com.coocaa.fire.utils.Utils;
import com.coocaa.fire.utils.exception.ImgCheckException;
import com.coocaa.fire.utils.exception.OriginException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class UploadifyUtils {

    /**
     * 根目录
     **/
    public final static String UPLOAD_ROOT;

    /**
     * Tvpie根目录
     */
    public final static String UPLOAD_TVPIE_ROOT;
    /**
     * URL前缀
     **/
    public final static String UPLOAD_URL;
    /**
     * 上传文件路径
     **/
    public final static String UPLOAD_PATH;
    /**
     * 上传文件路径
     **/
    public final static String VIDEO_ROOT = "/video/";
    /**
     * 上传图片大小
     **/
    final static String UPLOAD_PIC_SIZE;
    /**
     * 游戏中心图片缩略尺寸
     **/
    public final static String UPLOAD_GAME_ICON;
    /**
     * 壁纸管理缩略尺寸
     **/
    public final static String UPLOAD_WALLPAPER_ICON;
    /**
     * 影视首页图片类型
     **/
    public final static String[] UPLOAD_MOVIE_IMG_TYPE;
    /**
     * 影视首页图片大小
     **/
    public final static int UPLOAD_MOVIE_IMG_SIZE;

    /**
     * 自定义目录
     **/
    private static String CUSTOM_PATH;
    /**
     * 是否上传文件到CDN
     **/
    private static Boolean UPLOAD_TO_CND;
    // 将文件存储到CDN上
    private static ContinueFTP cftp = null;

    static {
        PropertiesUtil propertiesUtil = new PropertiesUtil();
        UPLOAD_ROOT = propertiesUtil.getString("upload.root");
        UPLOAD_TVPIE_ROOT = propertiesUtil.getString("upload.tvpieroot");
        UPLOAD_URL = propertiesUtil.getString("upload.url");
        UPLOAD_PATH = propertiesUtil.getString("upload.path");
        UPLOAD_PIC_SIZE = propertiesUtil.getString("upload.pic.size");
        UPLOAD_GAME_ICON = propertiesUtil.getString("upload.gameicon.size");
        UPLOAD_WALLPAPER_ICON = propertiesUtil.getString("upload.wallpaper.size");
        UPLOAD_MOVIE_IMG_TYPE = propertiesUtil.getString("upload.movieimg.type").split(",");
        UPLOAD_MOVIE_IMG_SIZE = Integer.parseInt(propertiesUtil.getString("upload.movieimg.size"));
        UPLOAD_TO_CND = propertiesUtil.getBoolean("upload_to_cdn");
    }

    /**
     * 原文件名
     */
    private String fileName;

    /**
     * 新文件名
     */
    private String newName;

    /**
     * 后缀名
     */
    private String extName;

    /**
     * 文件件类型路径
     */
    private String fileTypePath = "";

    /**
     * 文件大小
     */
    private String fileSize;

    /**
     * 返回信息
     */
    private Map<String, Object> resultMap = new HashMap<String, Object>();

    /**
     * 文件io流
     */
    private InputStream inputStream;


    public static String getFileMD5(String inputFile) throws Exception {


        // 缓冲区大小（这个可以抽出一个参数）

        int bufferSize = 256 * 1024;

        FileInputStream fileInputStream = null;

        DigestInputStream digestInputStream = null;


        try {

            // 拿到一个MD5转换器（同样，这里可以换成SHA1）

            MessageDigest messageDigest = MessageDigest.getInstance("MD5");


            // 使用DigestInputStream

            fileInputStream = new FileInputStream(inputFile);

            digestInputStream = new DigestInputStream(fileInputStream, messageDigest);


            // read的过程中进行MD5处理，直到读完文件

            byte[] buffer = new byte[bufferSize];

            while (digestInputStream.read(buffer) > 0) ;


            // 获取最终的MessageDigest

            messageDigest = digestInputStream.getMessageDigest();


            // 拿到结果，也是字节数组，包含16个元素

            byte[] resultByteArray = messageDigest.digest();


            // 同样，把字节数组转换成字符串

            return Hex.encodeHexString(resultByteArray);


        } catch (NoSuchAlgorithmException e) {

            return null;

        } finally {

            try {

                digestInputStream.close();

            } catch (Exception e) {

            }

            try {

                fileInputStream.close();

            } catch (Exception e) {

            }

        }

    }


    public UploadifyUtils() {
    }

    public UploadifyUtils(HttpServletRequest request, Boolean isCutImage) throws OriginException, IOException {
        CUSTOM_PATH = "";
        paserReqeust(request, isCutImage);
    }

    public UploadifyUtils(HttpServletRequest request) throws OriginException, IOException {
        CUSTOM_PATH = "";
        uploadDoc(request);
    }

    /**
     * 游戏中心上传视频和大Icon
     *
     * @param request
     * @param isVideo
     * @throws OriginException
     * @throws IOException
     */
    public UploadifyUtils(HttpServletRequest request, boolean isVideo) throws OriginException, IOException {
        CUSTOM_PATH = "";
        if (isVideo) {
            uploadVideo(request);
        } else {
            uploadBigIcon(request);
        }
    }

    /**
     * @param request
     * @param customPath --自定义路径
     * @param flag
     * @throws Exception
     */
    public UploadifyUtils(HttpServletRequest request, String customPath, Boolean flag) throws OriginException, IOException {
        CUSTOM_PATH = customPath;
        paserReqeust(request, false);
    }

    /**
     * 上传壁纸
     *
     * @param request
     * @param isCategory 1:壁纸分类图标，0：壁纸图片
     * @throws OriginException
     * @throws IOException
     */
    public UploadifyUtils(HttpServletRequest request, int isCategory) throws OriginException, IOException {
        CUSTOM_PATH = "";
        uploadWallpaper(request, isCategory);
    }

    /**
     * 上传影视首页素材图片
     *
     * @param request
     * @throws OriginException
     * @throws IOException
     */
    public UploadifyUtils(HttpServletRequest request, String size, Boolean checkResolution, Boolean checkImgWidth, String width) throws OriginException, IOException {
        CUSTOM_PATH = "";
        uploadHomeImg(request, size, checkResolution, checkImgWidth, width);
    }

    /**
     * 上传Tvpie广告图片
     *
     * @param request
     * @throws OriginException
     * @throws IOException
     */
    public UploadifyUtils(HttpServletRequest request, String imgSize, String remark, String tvpie) throws OriginException, IOException {
        CUSTOM_PATH = "";
        uploadTvpieImg(request, imgSize);
    }


//    /**
//     * @param request
//     * @param customPath --自定义路径
//     * @param flag
//     * @throws Exception
//     */
//    public UploadifyUtils2(HttpServletRequest request, String customPath, Boolean flag) throws OriginException, IOException {
//        CUSTOM_PATH = customPath;
//        paserReqeust(request, false);
//    }

    /**
     * 图像进行处理
     *
     * @param request
     * @param thumbnail
     */
    public UploadifyUtils(HttpServletRequest request, String thumbnail) {
        try {
            handlerImage(request, thumbnail);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 进行处理
     *
     * @param request
     * @param thumbnail
     * @throws Exception
     */
    @SuppressWarnings("static-access")
    private void handlerImage(HttpServletRequest request, String thumbnail) throws Exception {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {

            MultipartFile mf = entity.getValue();
            fileName = mf.getOriginalFilename();
            extName = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf("."), fileName.length()) : null;
            newName = URLUtils.uploadPath(Utils.getRandomLongStr() + extName, UPLOAD_ROOT);

            inputStream = mf.getInputStream();

            File uploadFile = new File(UPLOAD_PATH + newName);
            this.write(uploadFile);

            String[] imagesSizes = UPLOAD_PIC_SIZE.split(",");
            Integer index = 1;
            for (String string : imagesSizes) {
                String[] size = string.split("_");
                ImageUtil imageUtile = new ImageUtil();
                String outName = URLUtils.uploadPath(Utils.getRandomLongStr() + '_' + string + extName, UPLOAD_ROOT);
                imageUtile.resizeImage(UPLOAD_PATH + newName, UPLOAD_PATH + outName, Integer.parseInt(size[0]), Integer.parseInt(size[1]));
                resultMap.put("pic" + index, outName);
                index++;
            }

        }
    }

    /**
     * <b>paserReqeust</b><br/>
     * <p>解析request</p>
     * <b>date：</b>2014-6-17 下午4:40:43<br/>
     *
     * @param request
     * @param isCutImage --是否进行缩例图操作
     * @throws IOException
     * @throws Exception
     * @throws
     * @since 1.0
     */
    private void paserReqeust(HttpServletRequest request, Boolean isCutImage) throws OriginException, IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile mf = entity.getValue();
            fileName = mf.getOriginalFilename();
            extName = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf("."), fileName.length()) : null;

            String rootPath = Utils.getRandomLongStr();

            extName = extName.toLowerCase();
            if (extName.equals(".gif") || extName.equals(".png") || extName.equals(".jpg")) {
                fileTypePath = "/img/";
            } else if (extName.equals(".so")) {
                fileTypePath = "/so/";
            } else if (extName.equals(".apk")) {
                fileTypePath = "/apk/";
            } else {
                fileTypePath = "/apk/doc/";
            }
            newName = URLUtils.uploadPath(rootPath + extName, UPLOAD_ROOT);

            inputStream = mf.getInputStream();

            //判断图片宽度和高度是不符合设置要求
            if (!extName.equals(".apk")) {
                Integer minWidth = request.getParameter("imgMinWith") == null ? null : Integer.parseInt(request.getParameter("imgMinWith"));
                Integer minHeight = request.getParameter("imgMinHeight") == null ? null : Integer.parseInt(request.getParameter("imgMinHeight"));
                Image image = ImageIO.read(mf.getInputStream());
                if (minWidth != null && image.getWidth(null) < minWidth) {
                    throw new ImgCheckException("10001", "图片宽度不符合最小(" + minWidth + "px)要求!");
                }
                if (minHeight != null && image.getHeight(null) < minHeight) {
                    throw new ImgCheckException("10001", "图片高度不符合最小(" + minWidth + "px)要求!");
                }
            }

            //转换文件大小为M
            //DecimalFormat decFormat = new DecimalFormat("0.00");
            //fileSize = decFormat.format(new BigDecimal(inputStream.available()).divide(new BigDecimal(1048576))) + "M";
            fileSize = inputStream.available() + "";

            File uploadFile = new File(UPLOAD_PATH + fileTypePath + newName);
            this.write(uploadFile);

            // 进行缩例图操作
            if (isCutImage) {
                try {
                    String[] imagesSizes = UPLOAD_PIC_SIZE.split(",");
                    Integer index = 1;
                    for (String string : imagesSizes) {
                        String[] size = string.split("_");
                        ImageUtil imageUtile = new ImageUtil();
                        String outName = URLUtils.uploadPath(rootPath + '_' + string + extName, UPLOAD_ROOT);
                        imageUtile.resizeImage(UPLOAD_PATH + fileTypePath + newName, UPLOAD_PATH + fileTypePath + outName, Integer.parseInt(size[0]), Integer.parseInt(size[1]));
                        // ImageTools.cutImage(Integer.parseInt(size[0]),Integer.parseInt(size[1]), UPLOAD_PATH + newName,UPLOAD_PATH + outName);
                        resultMap.put("pic" + index, outName);

                        //cftp.startUpload(UPLOAD_PATH + fileTypePath + outName, fileTypePath + outName);
                        uploadToCDN(cftp, UPLOAD_PATH + fileTypePath + outName, fileTypePath + outName);
                        index++;
                    }
                } catch (Exception e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                    System.out.println("生成缩略图失败");
                }

            }
            if (extName.equals(".apk")) {
                new Thread(new Runnable() {
                    public void run() {
                        //开新线上传文件到CDN
                        uploadToCDN(cftp, UPLOAD_PATH + fileTypePath + newName, fileTypePath + newName);
                    }
                }).start();
            } else {
                uploadToCDN(cftp, UPLOAD_PATH + fileTypePath + newName, fileTypePath + newName);
            }
            //cftp.startUpload(UPLOAD_PATH + fileTypePath + newName, fileTypePath + newName);
        }
    }


    /**
     * 文档上传（只负责文档上传事宜）
     *
     * @param request
     * @return void
     * @throws OriginException
     * @throws IOException
     * @throws
     */
    private void uploadDoc(HttpServletRequest request) throws OriginException, IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        //所有图片格式
        String allImgType = ".BMP,.PCX,.TIFF,.GIF,.JPEG,.TGA,.EXIF,.FPX,.SVG,.PSD,.CDR,.PCD,.DXF,.UFO,.EPS,.AI,.PNG,.HDRI,.RAW";
        //文档根目录
        fileTypePath = "/apk/doc/";

        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile mf = entity.getValue();
            fileName = mf.getOriginalFilename();
            extName = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf("."), fileName.length()) : null;

            String rootPath = Utils.getRandomLongStr();

            //除图片外，其他文件都按后缀名分目录存储
            if (extName != null && allImgType.contains(extName.toUpperCase())) {
                newName = "img/" + URLUtils.uploadPath(rootPath + extName, UPLOAD_ROOT);
            } else {
                newName = extName.replace(".", "") + "/" + URLUtils.uploadPath(rootPath + extName, UPLOAD_ROOT);
            }

            inputStream = mf.getInputStream();

            fileSize = inputStream.available() + "";

            File uploadFile = new File(UPLOAD_PATH + fileTypePath + newName);
            this.write(uploadFile);

            if (extName.equals(".apk")) {
                new Thread(new Runnable() {
                    public void run() {
                        //开新线上传文件到CDN
                        uploadToCDN(cftp, UPLOAD_PATH + fileTypePath + newName, fileTypePath + newName);
                    }
                }).start();
            } else {
                uploadToCDN(cftp, UPLOAD_PATH + fileTypePath + newName, fileTypePath + newName);
            }
        }
    }

    /**
     * 上传视频文件
     *
     * @param request
     * @return void
     * @throws OriginException
     * @throws IOException
     * @throws
     */
    private void uploadVideo(HttpServletRequest request) throws OriginException, IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        //所有视频格式
        String allImgType = ".mkv,.rm,.rmvb,.mp4,.mov,.mtv,.wmv,.avi,.3gp,.amv,.flv,.mpg";
        //文档根目录
        fileTypePath = "/apk/video/";

        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile mf = entity.getValue();
            fileName = mf.getOriginalFilename();
            extName = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf("."), fileName.length()) : null;
            String rootPath = Utils.getRandomLongStr();
            //按后缀名分目录存储
            if (extName != null && allImgType.contains(extName.toLowerCase())) {
                newName = extName.replace(".", "") + "/" + URLUtils.uploadPath(rootPath + extName, UPLOAD_ROOT);
            }

            inputStream = mf.getInputStream();

            fileSize = inputStream.available() + "";

            File uploadFile = new File(UPLOAD_PATH + fileTypePath + newName);

            this.write(uploadFile);

            uploadToCDN(cftp, UPLOAD_PATH + fileTypePath + newName, fileTypePath + newName);

            newName = VIDEO_ROOT + newName;
        }
    }

    /**
     * 游戏中心上传BigIcon
     *
     * @param request
     * @return void
     * @throws OriginException
     * @throws IOException
     * @throws
     */
    @SuppressWarnings("static-access")
    private void uploadBigIcon(HttpServletRequest request) throws OriginException, IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile mf = entity.getValue();
            fileName = mf.getOriginalFilename();
            extName = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf("."), fileName.length()) : null;
            String rootPath = Utils.getRandomLongStr();
            fileTypePath = "/img/";
            newName = URLUtils.uploadPath(rootPath + extName, UPLOAD_ROOT);
            inputStream = mf.getInputStream();

            //判断图片宽度和高度是不符合设置要求
            if (!extName.equals(".apk")) {
                Integer minWidth = request.getParameter("imgMinWith") == null ? null : Integer.parseInt(request.getParameter("imgMinWith"));
                Integer minHeight = request.getParameter("imgMinHeight") == null ? null : Integer.parseInt(request.getParameter("imgMinHeight"));
                Image image = ImageIO.read(mf.getInputStream());
                if (minWidth != null && image.getWidth(null) < minWidth) {
                    throw new ImgCheckException("10001", "图片宽度不符合最小(" + minWidth + "px)要求!");
                }
                if (minHeight != null && image.getHeight(null) < minHeight) {
                    throw new ImgCheckException("10001", "图片高度不符合最小(" + minWidth + "px)要求!");
                }
            }

            fileSize = inputStream.available() + "";

            File uploadFile = new File(UPLOAD_PATH + fileTypePath + newName);
            this.write(uploadFile);

            // 进行缩例图操作
            try {
                String[] imagesSizes = UPLOAD_GAME_ICON.split(",");
                Integer index = 1;
                for (String string : imagesSizes) {
                    String[] size = string.split("_");
                    ImageUtil imageUtile = new ImageUtil();
                    String outName = URLUtils.uploadPath(rootPath + '_' + string + extName, UPLOAD_ROOT);
                    imageUtile.resizeImage(UPLOAD_PATH + fileTypePath + newName, UPLOAD_PATH + fileTypePath + outName, Integer.parseInt(size[0]), Integer.parseInt(size[1]));
                    resultMap.put("pic" + index, outName);
                    uploadToCDN(cftp, UPLOAD_PATH + fileTypePath + outName, fileTypePath + outName);
                    index++;
                }
            } catch (Exception e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
                System.out.println("生成缩略图失败");
            }

            uploadToCDN(cftp, UPLOAD_PATH + fileTypePath + newName, fileTypePath + newName);
        }
    }


    /**
     * 上传壁纸
     *
     * @param request
     * @return void
     * @throws OriginException
     * @throws IOException
     * @throws
     */
    @SuppressWarnings("static-access")
    private void uploadWallpaper(HttpServletRequest request, int isCategory) throws OriginException, IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile mf = entity.getValue();
            fileName = mf.getOriginalFilename();
            extName = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf("."), fileName.length()) : null;
            extName = extName.toLowerCase();
            if (isCategory == 1) {
                if (extName != null && !extName.equals(".jpg") && !extName.equals(".jpeg") && !extName.equals(".png")) {
                    throw new ImgCheckException("10001", "图片必须为.jpg、.jpeg格式或.png!");
                }
            } else {
                if (extName != null && !extName.equals(".jpg") && !extName.equals(".jpeg")) {
                    throw new ImgCheckException("10001", "图片必须为.jpg或.jpeg格式!");
                }
            }
            String rootPath = Utils.getRandomLongStr();
            fileTypePath = "/img/";
            newName = URLUtils.uploadPath(rootPath + extName, UPLOAD_ROOT);
            inputStream = mf.getInputStream();

            //判断图片宽度和高度是不符合设置要求
            if (!extName.equals(".apk")) {
                Integer minWidth = request.getParameter("imgMinWith") == null ? null : Integer.parseInt(request.getParameter("imgMinWith"));
                Integer minHeight = request.getParameter("imgMinHeight") == null ? null : Integer.parseInt(request.getParameter("imgMinHeight"));
                Image image = ImageIO.read(mf.getInputStream());
                if (minWidth != null && image.getWidth(null) != minWidth) {
                    throw new ImgCheckException("10001", "图片宽度不符合(" + minWidth + "px)要求!");
                }
                if (minHeight != null && image.getHeight(null) != minHeight) {
                    throw new ImgCheckException("10001", "图片高度不符合(" + minWidth + "px)要求!");
                }
            }

            fileSize = inputStream.available() + "";
            File uploadFile = new File(UPLOAD_PATH + fileTypePath + newName);
            this.write(uploadFile);

            // 进行缩例图操作
            try {
                String[] imagesSizes = UPLOAD_WALLPAPER_ICON.split(",");
                Integer index = 1;
                for (String string : imagesSizes) {
                    String[] size = string.split("_");
                    ImageUtil imageUtile = new ImageUtil();
                    String outName = URLUtils.uploadPath(rootPath + '_' + string + extName, UPLOAD_ROOT);
                    imageUtile.resizeImage(UPLOAD_PATH + fileTypePath + newName, UPLOAD_PATH + fileTypePath + outName, Integer.parseInt(size[0]), Integer.parseInt(size[1]));
                    resultMap.put("pic" + index, "/" + outName);
                    uploadToCDN(cftp, UPLOAD_PATH + fileTypePath + outName, fileTypePath + outName);
                    index++;
                }
            } catch (Exception e2) {
                // TODO Auto-generated catch block
                e2.printStackTrace();
                System.out.println("生成缩略图失败");
            }

            uploadToCDN(cftp, UPLOAD_PATH + fileTypePath + newName, fileTypePath + newName);
        }
    }


    /**
     * 上传影视首页素材图片
     *
     * @param request
     * @param iSize           图片分辨率
     * @param checkResolution 是否检查分辨率 true/false
     * @return void
     * @throws OriginException
     * @throws IOException
     * @throws
     */
    @SuppressWarnings("static-access")
    private void uploadHomeImg(HttpServletRequest request, String iSize, Boolean checkResolution, Boolean checkImgWidth, String imgWidthstr) throws OriginException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile mf = entity.getValue();
            fileName = mf.getOriginalFilename();
            extName = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf("."), fileName.length()) : null;
            extName = extName.toLowerCase();

            boolean isflag = false;    //false不合法 true合法
            String er = "";
            for (String s : UPLOAD_MOVIE_IMG_TYPE) {
                if (extName != null && extName.equals(s)) {
                    isflag = true;
                    break;
                }
                er += s;
            }
            if (!isflag) {
                throw new ImgCheckException("10001", "图片必须为" + er.toString() + "格式!");
            }

            String rootPath = Utils.getRandomLongStr();
            fileTypePath = "/img/";
            BufferedImage bufferedImage = ImageIO.read(mf.getInputStream());

            int imgWidth = 0;
            int imgHeight = 0;
            if ((".webp").equalsIgnoreCase(extName)) {
//                ImageReader ir = new WebPImageReader(new WebPImageReaderSpi());//读取WEBP格式图片插件
//                try {
//                    ir.setInput(ImageIO.createImageInputStream(mf.getInputStream()));
//                    imgWidth = ir.getWidth(0);
//                    imgHeight = ir.getHeight(0);
//                    System.out.print("w:" + imgWidth + ",高:" + imgHeight);
//                } catch (Exception e) {
//                    e.getMessage();
//                }

//                try {
//                    InputStream is = mf.getInputStream();
//                    byte[] bytes = new byte[30];
//                    is.read(bytes);
//                    if ("WEBP".equals(new String(bytes, 8, 4, "utf-8"))) {//先判断图片格式为webp
//                        int w = (((int) bytes[27] & 0xff) << 8) | (int) bytes[26] & 0xff;
//                        int h = (((int) bytes[29] & 0xff) << 8) | (int) bytes[28] & 0xff;
//                        System.out.print("w:" + w + ",高:" + h);
//                    } else {
//                        System.out.print("文件不是webp格式");
//                    }
//                    is.close();
//
//                } catch (Exception e) {
//                    System.out.println(e.getMessage());
//                }
            } else {
                imgWidth = bufferedImage.getWidth();
                imgHeight = bufferedImage.getHeight();
            }
            newName = URLUtils.uploadPath(rootPath + "_" + imgWidth + "x" + imgHeight + extName, UPLOAD_ROOT);       // 获取新的文件名
            inputStream = mf.getInputStream();

            //图片宽度是否符合要求
            if (null != checkImgWidth && !extName.equals(".webp") && checkImgWidth) {
                resultMap.put("fileType", "front");                                // 图片类型(front)
                boolean imgFlag = false;
                if ((imgWidthstr.length() > 0)) {
                    String[] imgWidthArrs = imgWidthstr.split(";");
                    for (int i = 0; i < imgWidthArrs.length; i++) {
                        if (imgWidth == Integer.parseInt(imgWidthArrs[i])) {
                            imgFlag = true;
                            break;
                        }
                    }
                    if (!imgFlag) {
                        throw new ImgCheckException("10001", "图片宽度" + imgWidth + "不符合要求!");
                    }
                }

                fileSize = inputStream.available() + "";
                long imgSize = Integer.parseInt(fileSize) / 1024;
                if (imgSize == 0) {
                    imgSize = mf.getSize() / 1024;
                }
                System.out.println("imgSize: -->" + imgSize + "KB");
                if (imgSize > UPLOAD_MOVIE_IMG_SIZE) {
                    throw new ImgCheckException("10001", "图片大小不符合(" + UPLOAD_MOVIE_IMG_SIZE + "KB)要求!");
                }
            }


            //判断图片宽度和高度和大小是不符合设置要求
            if (null != checkResolution && !extName.equals(".webp") && checkResolution) {
                resultMap.put("fileType", "background");                                // 图片类型(background)
                boolean resolutionFlag = false;
                if ((iSize.length() > 0)) {
                    String[] resolutionArrs = iSize.split(";");
                    for (int i = 0; i < resolutionArrs.length; i++) {
                        if ((imgWidth + "x" + imgHeight).contains(resolutionArrs[i])) {
                            resolutionFlag = true;
                            break;
                        }
                    }
                    if (!resolutionFlag) {
                        throw new ImgCheckException("10001", "图片尺寸不符合要求!");
                    }
                }

                fileSize = inputStream.available() + "";
                long imgSize = Integer.parseInt(fileSize) / 1024;
                if (imgSize == 0) {
                    imgSize = mf.getSize() / 1024;
                }
                System.out.println("imgSize: -->" + imgSize + "KB");
                if (imgSize > UPLOAD_MOVIE_IMG_SIZE) {
                    throw new ImgCheckException("10001", "图片大小不符合(" + UPLOAD_MOVIE_IMG_SIZE + "KB)要求!");
                }
            }

            fileSize = inputStream.available() + "";
            String localFilePath = UPLOAD_PATH + fileTypePath + newName;        // 本机保存路径
            String remoteFilePaht = fileTypePath + newName;                     // 远程保存路径
            File uploadFile = new File(localFilePath);
            String fileMd5 = "";
            this.write(uploadFile);                                             // 保存在本机
            try {
                fileMd5 = getFileMD5(localFilePath);
            } catch (Exception e) {
            }
            resultMap.put("md5", fileMd5);                                     // 图片md5值

            resultMap.put("resolution", imgWidth + "x" + imgHeight);               // 图片分辨率
            uploadToCDN(cftp, localFilePath, remoteFilePaht);                   // 上传到FTP
        }
    }


    /**
     * 上传Tvpie图片
     *
     * @param request
     * @param iSize   图片分辨率
     * @return void
     * @throws OriginException
     * @throws IOException
     * @throws
     */
    @SuppressWarnings("static-access")
    private void uploadTvpieImg(HttpServletRequest request, String iSize) throws OriginException, IOException {

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile mf = entity.getValue();
            fileName = mf.getOriginalFilename();
            extName = fileName.indexOf(".") != -1 ? fileName.substring(fileName.lastIndexOf("."), fileName.length()) : null;
            extName = extName.toLowerCase();

//			if (extName != null && !extName.equals(".jpg") && !extName.equals(".jpeg")) {
            boolean isflag = false;    //false不合法 true合法
            String er = "";
            for (String s : UPLOAD_MOVIE_IMG_TYPE) {
                if (extName != null && extName.equals(s)) {
                    isflag = true;
                }
                er += s;
            }
            if (!isflag) {
                throw new ImgCheckException("10001", "图片必须为" + er.toString() + "格式!");
            }

            String rootPath = Utils.getRandomLongStr();
            fileTypePath = "/img/";
            iSize = iSize == null ? "" : "_" + iSize;
//            newName = URLUtils.uploadPath(rootPath  + iSize + extName, UPLOAD_TVPIE_ROOT);       // 获取新的文件名
            newName = URLUtils.uploadPath(rootPath + iSize + extName, "tvpie_images");       // 获取新的文件名 取不到配置文件夹值暂时写死
            inputStream = mf.getInputStream();

            //判断图片宽度和高度和大小是不符合设置要求
            StringBuilder imgPix = new StringBuilder();
            if (!extName.equals(".apk")) {
                Integer minWidth = request.getParameter("imgMinWith") == null ? null : Integer.parseInt(request.getParameter("imgMinWith"));
                Integer minHeight = request.getParameter("imgMinHeight") == null ? null : Integer.parseInt(request.getParameter("imgMinHeight"));
                BufferedImage bufferedImage = ImageIO.read(mf.getInputStream());

                Integer imageWidth = bufferedImage.getWidth();
                Integer imageHeight = bufferedImage.getHeight();
                resultMap.put("imageWidth", imageWidth);
                resultMap.put("imageHeight", imageHeight);

                if (minWidth != null && imageWidth != minWidth) {
                    throw new ImgCheckException("10001", "图片宽度不符合(" + minWidth + "px)要求!");
                }
                if (minHeight != null && imageHeight != minHeight) {
                    throw new ImgCheckException("10001", "图片高度不符合(" + minWidth + "px)要求!");
                }

                fileSize = inputStream.available() + "";
                long imgSize = Integer.parseInt(fileSize) / 1024;
                if (imgSize == 0) {
                    imgSize = mf.getSize() / 1024;
                }
                System.out.println("imgSize: -->" + imgSize + "KB");
                if (imgSize > UPLOAD_MOVIE_IMG_SIZE) {
                    throw new ImgCheckException("10001", "图片大小不符合(" + UPLOAD_MOVIE_IMG_SIZE + "KB)要求!");
                }
            }

            fileSize = inputStream.available() + "";
            String localFilePath = UPLOAD_PATH + fileTypePath + newName;        // 本机保存路径
            String remoteFilePaht = fileTypePath + newName;                     // 远程保存路径
            File uploadFile = new File(localFilePath);
            this.write(uploadFile);                                             // 保存在本机
            uploadToCDN(cftp, localFilePath, remoteFilePaht);                   // 上传到FTP
        }

    }


    /**
     * 保存文件
     *
     * @param saveFile
     * @throws IOException
     */
    public void write(File saveFile) throws IOException {
//        if (!saveFile.getParentFile().exists()) {
//            FileUtils.forceMkdir(saveFile.getParentFile());
//        }
//        OutputStream output = new FileOutputStream(saveFile);
//
//        IOUtils.copy(getInputStream(), output);
//        IOUtils.closeQuietly(output);
    }

    private void uploadToCDN(ContinueFTP cftp, String localFile, String remoteFile) {
        if (UPLOAD_TO_CND == true) {
            if (cftp == null) {
                cftp = new ContinueFTP();
            }
            try {
                cftp.startUpload(localFile, remoteFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getExtName() {
        return extName;
    }

    public void setExtName(String extName) {
        this.extName = extName;
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    public String getNewName() {
        return newName;
    }

    public void setNewName(String newName) {
        this.newName = newName;
    }

    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    public String getFileSize() {
        return fileSize;
    }

    public String getFileTypePath() {
        return fileTypePath;
    }

    public void setFileTypePath(String fileTypePath) {
        this.fileTypePath = fileTypePath;
    }
}
