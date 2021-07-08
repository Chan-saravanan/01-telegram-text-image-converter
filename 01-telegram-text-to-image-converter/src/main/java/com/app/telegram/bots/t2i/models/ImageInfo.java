package com.app.telegram.bots.t2i.models;

public class ImageInfo {
	private String text;
	private Integer width;
	private Integer height;
	private String fontStyle;
	private Integer captionLimit;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Integer getWidth() {
		return width;
	}
	public void setWidth(Integer width) {
		this.width = width;
	}
	public Integer getHeight() {
		return height;
	}
	public void setHeight(Integer height) {
		this.height = height;
	}
	
	public String getFontStyle() {
		return fontStyle;
	}
	public void setFontStyle(String fontStyle) {
		this.fontStyle = fontStyle;
	}
	public Integer getCaptionLimit() {
		return captionLimit;
	}
	public void setCaptionLimit(Integer captionLimit) {
		this.captionLimit = captionLimit;
	}
	@Override
	public String toString() {
		return "ImageInfo [text=" + text + ", width=" + width + ", height=" + height + ", fontStyle=" + fontStyle
				+ ", captionLimit=" + captionLimit + "]";
	}
}
