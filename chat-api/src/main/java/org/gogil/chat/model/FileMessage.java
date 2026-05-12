package org.gogil.chat.model;

public class FileMessage extends  Message{
    private String fileName;
    private String fileType;
    private String fileContent;
    private long fileSize;

    public FileMessage() {
        super(MessageType.FILE_MESSAGE);
    }

    public FileMessage(String roomName, String userName,
                       String fileName, String fileType,
                       String fileContent, long fileSize) {
        super(MessageType.FILE_MESSAGE, roomName, userName);
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileContent = fileContent;
        this.fileSize = fileSize;
    }

    public boolean isImage() {
        return fileType != null && fileType.startsWith("image");
    }

    public boolean isVideo() {
        return fileType != null && fileType.startsWith("video");
    }

    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }
    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileContent() {
        return fileContent;
    }
    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public long getFileSize() {
        return fileSize;
    }
    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
}
