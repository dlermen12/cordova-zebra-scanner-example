/*
 Licensed to the Apache Software Foundation (ASF) under one
 or more contributor license agreements.  See the NOTICE file
 distributed with this work for additional information
 regarding copyright ownership.  The ASF licenses this file
 to you under the Apache License, Version 2.0 (the
 "License"); you may not use this file except in compliance
 with the License.  You may obtain a copy of the License at
 http://www.apache.org/licenses/LICENSE-2.0
 Unless required by applicable law or agreed to in writing,
 software distributed under the License is distributed on an
 "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 KIND, either express or implied.  See the License for the
 specific language governing permissions and limitations
 under the License.
 */


package com.byond.cordova.plugin.zebra;


import java.util.List;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.apache.cordova.PluginResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zebra.rfid.api3.RFIDReader;
import com.zebra.rfid.api3.RFModeTable;
import com.zebra.rfid.api3.InvalidUsageException;
import com.zebra.rfid.api3.OperationFailureException;
import com.zebra.rfid.api3.ReaderDevice;
import com.zebra.rfid.api3.Readers;


public class ZebraMain extends CordovaPlugin {

  public static final String TAG = ZebraMain.class.getSimpleName();
  private String rootPath = "/";
  private Readers readers;

  @Override
  public boolean execute(String action, final JSONArray args, final CallbackContext callbackContext) throws JSONException {

    if (readers == null) {
      readers = new Readers();
    }

    if (action.equals("list")) {
      list(args, callbackContext);
    }

    if (action.equals("connect")) {
      connect(args, callbackContext);
    }

    return true;
  }


  public void connect(final JSONArray args, final CallbackContext callbackContext) {

    cordova.getThreadPool().execute(new Runnable() {
      public void run() {

        try {
          String name = "denner-mudar-aqui";
          List<ReaderDevice> readersList = readers.GetAvailableRFIDReaderList();
          ReaderDevice readDevice = null;
          for (ReaderDevice device : readersList){
              if(device.getName().equals(name)){
                readDevice = device;
              }
          }
          if(readDevice == null){
            callbackContext.error("Device not found");
            return;
          }

          RFIDReader rfidReader = readDevice.getRFIDReader();
          JSONArray fileList = new JSONArray();
//
//          for (int idx = 0; idx < readersList.size(); idx++) {
//            ReaderDevice device = readersList.get(idx);
//            String name = device.getName();
//            String address = device.getAddress();
//            String jsonStr = "{" + "name:\"" + name + "\", address:" + address + "\"}";
//            JSONObject jsonObj = new JSONObject(jsonStr);
//            fileList.put(jsonObj);
//          }

          callbackContext.success(fileList);
        } catch (Exception e) {
          callbackContext.error(e.toString());
        }
      }
    });
  }


  public void list(final JSONArray args, final CallbackContext callbackContext) {

    cordova.getThreadPool().execute(new Runnable() {
      public void run() {

        try {
          List<ReaderDevice> readersList = readers.GetAvailableRFIDReaderList();
          JSONArray fileList = new JSONArray();

          for (int idx = 0; idx < readersList.size(); idx++) {
            ReaderDevice device = readersList.get(idx);
            String name = device.getName();
            String address = device.getAddress();
            String jsonStr = "{" + "name:\"" + name + "\", address:" + address + "\"}";
            JSONObject jsonObj = new JSONObject(jsonStr);
            fileList.put(jsonObj);
          }

          callbackContext.success(fileList);
        } catch (Exception e) {
          callbackContext.error(e.toString());
        }
      }
    });

//
//    } else if (action.equals("list")) {
//      cordova.getThreadPool().execute(new Runnable() {
//        public void run() {
//          try {
//            list(args.getString(0), callbackContext);
//          } catch (Exception e) {
//            callbackContext.error(e.toString());
//          }
//        }
//      });
//    } else if (action.equals("createDirectory")) {
//      cordova.getThreadPool().execute(new Runnable() {
//        public void run() {
//          try {
//            createDirectory(args.getString(0), callbackContext);
//          } catch (Exception e) {
//            callbackContext.error(e.toString());
//          }
//        }
//      });
//    } else if (action.equals("deleteDirectory")) {
//      cordova.getThreadPool().execute(new Runnable() {
//        public void run() {
//          try {
//            deleteDirectory(args.getString(0), callbackContext);
//          } catch (Exception e) {
//            callbackContext.error(e.toString());
//          }
//        }
//      });
//    } else if (action.equals("deleteFile")) {
//      cordova.getThreadPool().execute(new Runnable() {
//        public void run() {
//          try {
//            deleteFile(args.getString(0), callbackContext);
//          } catch (Exception e) {
//            callbackContext.error(e.toString());
//          }
//        }
//      });
//    } else if (action.equals("uploadFile")) {
//      cordova.getThreadPool().execute(new Runnable() {
//        public void run() {
//          try {
//            uploadFile(args.getString(0), args.getString(1), callbackContext);
//          } catch (Exception e) {
//            callbackContext.error(e.toString());
//          }
//        }
//      });
//    } else if (action.equals("downloadFile")) {
//      cordova.getThreadPool().execute(new Runnable() {
//        public void run() {
//          try {
//            downloadFile(args.getString(0), args.getString(1), callbackContext);
//          } catch (Exception e) {
//            callbackContext.error(e.toString());
//          }
//        }
//      });
//    } else if (action.equals("cancelAllRequests")) {
//      cordova.getThreadPool().execute(new Runnable() {
//        public void run() {
//          try {
//            cancelAllRequests(callbackContext);
//          } catch (Exception e) {
//            callbackContext.error(e.toString());
//          }
//        }
//      });
//    } else if (action.equals("disconnect")) {
//      cordova.getThreadPool().execute(new Runnable() {
//        public void run() {
//          try {
//            disconnect(callbackContext);
//          } catch (Exception e) {
//            callbackContext.error(e.toString());
//          }
//        }
//      });
//    } else {
//      // This action/cmd is not found/supported
//      return false;
//    }
    // This action/cmd is found/supported
  }

//  private void connect(String hostname, String username, String password, CallbackContext callbackContext) {
//    if (hostname == null || hostname.length() <= 0)
//    {
//      callbackContext.error("Expected hostname.");
//    }
//    else
//    {
//      if (username == null && password == null)
//      {
//        username = "anonymous";
//        password = "anonymous@";
//      }
//
//      try {
//        this.client = new FTPClient();
//        String[] address = hostname.split(":");
//        if (address.length == 2) {
//          String host = address[0];
//          int port = Integer.parseInt(address[1]);
//          this.client.connect(host, port);
//        } else {
//          this.client.connect(hostname);
//        }
//        this.client.login(username, password);
//        callbackContext.success("Connect and login OK.");
//      } catch (Exception e) {
//        callbackContext.error(e.toString());
//      }
//    }
//  }
//
//  private void list(String path, CallbackContext callbackContext) {
//    if (path == null)
//    {
//      callbackContext.error("Expected path.");
//    }
//    else
//    {
//      if (!path.endsWith("/"))
//      {
//        path = path.concat("/");
//      }
//
//      try {
//        this.client.changeDirectory(path);
//        FTPFile[] list = client.list();
  //        JSONArray fileList = new JSONArray();
  //        for (FTPFile file:list) {
  //          String name = file.getName();
  //          Number type = file.getType();
  //          String link = file.getLink();
  //          Number size = file.getSize();
  //          Date modifiedDate = file.getModifiedDate();
  //          String modifiedDateString = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss zzz")).format(modifiedDate);
  //          String jsonStr = "{" + "name:\"" + name + "\",type:" + type + ",link:\"" + link + "\",size:" + size + ",modifiedDate:\"" + modifiedDateString + "\"}";
  //          JSONObject jsonObj = new JSONObject(jsonStr);
  //          fileList.put(jsonObj);
  //        }
  //        callbackContext.success(fileList);
//      } catch (Exception e) {
//        callbackContext.error(e.toString());
//      }
//    }
//  }
//
//  private void createDirectory(String path, CallbackContext callbackContext) {
//    if (path == null)
//    {
//      callbackContext.error("Expected path.");
//    }
//    else
//    {
//      if (!path.endsWith("/"))
//      {
//        path = path.concat("/");
//      }
//
//      try {
//        this.client.changeDirectory(this.rootPath);
//        this.client.createDirectory(path);
//        callbackContext.success("Create directory OK");
//      } catch (Exception e) {
//        callbackContext.error(e.toString());
//      }
//    }
//  }
//
//  private void deleteDirectory(String path, CallbackContext callbackContext) {
//    if (path == null)
//    {
//      callbackContext.error("Expected path.");
//    }
//    else
//    {
//      if (!path.endsWith("/"))
//      {
//        path = path.concat("/");
//      }
//
//      try {
//        this.client.changeDirectory(this.rootPath);
//        this.client.deleteDirectory(path);
//        callbackContext.success("Delete directory OK");
//      } catch (Exception e) {
//        callbackContext.error(e.toString());
//      }
//    }
//  }
//
//  private void deleteFile(String file, CallbackContext callbackContext) {
//    if (file == null)
//    {
//      callbackContext.error("Expected file.");
//    }
//    else
//    {
//      try {
//        this.client.changeDirectory(this.rootPath);
//        this.client.deleteFile(file);
//        callbackContext.success("Delete file OK");
//      } catch (Exception e) {
//        callbackContext.error(e.toString());
//      }
//    }
//  }
//
//  private void uploadFile(String localFile, String remoteFile, CallbackContext callbackContext) {
//    if (localFile == null || remoteFile == null)
//    {
//      callbackContext.error("Expected localFile and remoteFile.");
//    }
//    else
//    {
//      try {
//        String remoteFilePath = remoteFile.substring(0, remoteFile.lastIndexOf('/') + 1);
//        String remoteFileName = remoteFile.substring(remoteFile.lastIndexOf('/') + 1);
//        String localFilePath = localFile.substring(0, localFile.lastIndexOf('/') + 1);
//        String localFileName = localFile.substring(localFile.lastIndexOf('/') + 1);
//        this.client.changeDirectory(remoteFilePath);
//        File file = new File(localFile);
//        InputStream in =  new FileInputStream(file);
//        long size = file.length();
//        client.upload(remoteFileName, in, 0, 0, new ZebraTransferListener(size, callbackContext));
//        // refer to ZebraTransferListener for transfer percent and completed
//      } catch (Exception e) {
//        callbackContext.error(e.toString());
//      }
//    }
//  }
//
//  private void downloadFile(String localFile, String remoteFile, CallbackContext callbackContext) {
//    if (localFile == null || remoteFile == null)
//    {
//      callbackContext.error("Expected localFile and remoteFile.");
//    }
//    else
//    {
//      try {
//        String remoteFilePath = remoteFile.substring(0, remoteFile.lastIndexOf('/') + 1);
//        String remoteFileName = remoteFile.substring(remoteFile.lastIndexOf('/') + 1);
//        this.client.changeDirectory(remoteFilePath);
//        FTPFile[] list = client.list();
//        JSONArray fileList = new JSONArray();
//        for (FTPFile file:list) {
//          String name = file.getName();
//          //Number type = file.getType();
//          //String link = file.getLink();
//          Number size = file.getSize();
//          if (remoteFileName.equals(name)) {
//            client.download(remoteFileName, new File(localFile), new ZebraTransferListener(size.longValue(), callbackContext));
//            // refer to ZebraTransferListener for transfer percent and completed
//            return;
//          }
//        }
//        // should never reach here!
//      } catch (Exception e) {
//        callbackContext.error(e.toString());
//      }
//    }
//  }
//
//  private void cancelAllRequests(CallbackContext callbackContext) {
//    try {
//      // `true` to perform a legal abort procedure (an ABOR command is sent to the server),
//      // `false` to abruptly close the transfer without advice.
//      this.client.abortCurrentDataTransfer(true);
//      callbackContext.success("Cancel OK.");
//    } catch (Exception e) {
//      callbackContext.error(e.toString());
//    }
//  }
//
//  private void disconnect(CallbackContext callbackContext) {
//    try {
//      // `true` to perform a legal disconnect procedure (an QUIT command is sent to the server),
//      // `false` to break the connection without advice.
//      this.client.disconnect(true);
//      callbackContext.success("Disconnect OK.");
//    } catch (Exception e) {
//      callbackContext.error(e.toString());
//    }
//  }
}

