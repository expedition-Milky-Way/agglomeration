package com.deystar.Zip;

import com.deystar.Result.ResultState;
import com.deystar.UserTyper.UserTyper;
import com.deystar.Zip.Entity.FileListBean;
import com.deystar.Zip.SevenZipCommand.CommandBuilder;
import com.deystar.Zip.SevenZipCommand.SevenZipCommand;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @Author YeungLuhyun
 **/
public class GoZip {
    private FileListBean bean;

    private UserTyper user;
    SevenZipCommand sevenZipCommand = new CommandBuilder();

    public void start() {

        String command;
        if (user.getIsEncryption()) {
            command = sevenZipCommand.hasPassword(user.getSevenZipPath())
                    .outPut(bean.getZipName()).password(user.getPassword())
                    .append(bean.getFileLit())
                    .build();
        } else {
            command = sevenZipCommand.noPassword(user.getSevenZipPath())
                    .outPut(bean.getZipName())
                    .append(bean.getFileLit())
                    .build();
        }
        try {
            this.zip(command);
        } catch (IOException | InterruptedException e) {

            String message = new RuntimeException(e).getMessage();
            System.out.println("命令："+command+" 异常："+message);
        }
    }

    public void zip(String command) throws IOException, InterruptedException {
        Process proc = Runtime.getRuntime().exec(command);
        InputStream stdIn = proc.getInputStream();
        InputStreamReader isr = new InputStreamReader(stdIn, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);

        String line = null;


        while ((line = br.readLine()) != null)
            System.out.println(line);


        int exitVal = proc.waitFor();
        System.out.println("Process exitValue: " + exitVal);
        
    }

    public GoZip(UserTyper user, FileListBean bean) {
        this.user = user;
        this.bean = bean;

    }
}
