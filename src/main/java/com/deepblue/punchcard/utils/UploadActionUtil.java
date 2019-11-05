package com.deepblue.punchcard.utils;

import com.deepblue.punchcard.customException.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 文件上传路径工具类
 */
public class UploadActionUtil {

    private static Logger logger = LoggerFactory.getLogger(UploadActionUtil.class);

    private static String[] suffixArr = {".jpg",".png",".jpeg",".bmp"};

    /**
     * 上传过程
     *
     * @param request
     * @return
     */
    public static List<String> uploadFiled(HttpServletRequest request) throws IOException {
        //获取项目部署根路径
        String rootPath = request.getSession().getServletContext().getRealPath("");
        logger.info("项目根路径为："+rootPath);
        //上传文件的路径
        String fileSavePath = rootPath+"img";
        ArrayList<String> list = new ArrayList<>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断是否有上传文件
        if (multipartResolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            Iterator<String> fileNames = multiRequest.getFileNames();
            while (fileNames.hasNext()) {
                //获取上传的文件
                MultipartFile file = multiRequest.getFile(fileNames.next());
                if (file != null) {
                    //获取当前上传文件的文件名
                    String uploadFilename = file.getOriginalFilename();
                    logger.info("上传文件的文件名为:" + uploadFilename);
                    if (uploadFilename.trim() != "") {
                        //获取文件后缀名
                        String uploadFileSuffix = uploadFilename.substring(uploadFilename.lastIndexOf("."));
                        //判断文件格式是否正确
                        boolean contains = Arrays.asList(suffixArr).contains(uploadFileSuffix);
                        if (contains) {
                            //重命名文件
                            String fileName = getCurrDateAsFileName() + uploadFileSuffix;
                            //创建文件夹
                            String filePath = fileSavePath + File.separator + folderName();
                            File fileFolder = new File(filePath);
                            if(!fileFolder.exists() && !fileFolder.isDirectory()){
                                fileFolder.mkdirs();
                            }
                            File wholeFile = new File(filePath + File.separator + fileName);
                            file.transferTo(wholeFile);
                            //组织返回数据
                            uploadFilename = folderName() + File.separator + fileName;
                            list.add(fileSavePath+"//"+uploadFilename);
                        } else {
                            throw new ServiceException("文件格式不正确");
                        }
                    }
                }

            }
        }
        return list;
    }


    /**
     * 以当前日期生成当前文件名
     *
     * @return
     */
    private static String getCurrDateAsFileName() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR) + "" + (now.get(Calendar.MONTH) + 1) + "" + now.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 生成文件夹
     *
     * @return
     */
    private static String folderName() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date());
    }


}
