package com.app.telegram.bots.t2i;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.app.telegram.bots.t2i.models.ImageInfo;

@Service
public class ImageTransformService {
	private static final Logger logger = Logger.getLogger(ImageTransformService.class.getName());
	
	public void directTextToImage(OutputStream os, ImageInfo imageInfo) {
		convertTexttoImage(os, imageInfo);
	}
	
	public void camelTextToImage(OutputStream os, ImageInfo imageInfo) {
		String s= new String();
		String camelText = Arrays.asList(imageInfo.getText().split("\\s"))
		.stream()
		.map(string->String.valueOf(string.charAt(0)))
		.limit(2)
		.collect(Collectors.joining());  
		
		logger.info("Camel Text : "+camelText);
		
		imageInfo.setText(camelText);
		
		convertTexttoImage(os, imageInfo);
	}
	
	public InputStream camelTextToImageStream(ImageInfo imageInfo) {
		String s= new String();
		String camelText = Arrays.asList(imageInfo.getText().split("\\s"))
		.stream()
		.map(string->String.valueOf(string.charAt(0)))
		.limit(2)
		.collect(Collectors.joining());  
		
		logger.info("Camel Text : "+camelText);
		
		imageInfo.setText(camelText);
		
		return convertTexttoImageStream(imageInfo);
	}
	
	public void convertTexttoImage(OutputStream os, ImageInfo imageInfo)
	{
		BufferedImage bImage =new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bImage.createGraphics();
		
		Font font = new Font(imageInfo.getFontStyle(), Font.PLAIN, 30);
		g2d.setFont(font);
		
		FontMetrics fontMetrics = g2d.getFontMetrics(font);
		
		int width = fontMetrics.stringWidth(imageInfo.getText());
		int height = fontMetrics.getHeight();
		g2d.dispose();
		logger.info("width :"+width+" :: height :"+height);
		
		bImage = new BufferedImage(width+14+(width*(35/100)), height+14+(height*(35/100)), BufferedImage.TYPE_INT_RGB);
		g2d = bImage.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
        Color backGroundColor = prepareBackGroundColor();
        Color fontColor = prepareFontColor();
        fontMetrics = g2d.getFontMetrics();
        g2d.setColor(backGroundColor);
        g2d.fillRect(0, 0, bImage.getWidth(), bImage.getHeight());
        g2d.setColor(fontColor);
        g2d.drawString(imageInfo.getText(), 7, height-(height*(10/100)));
		g2d.dispose();
		try {
			ImageIO.write(bImage, "png", os);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private Color prepareFontColor() {
		Color fontColor = new Color(255, 255, 224);
		return fontColor;
	}
	
	private Color prepareBackGroundColor() {
		Integer r = new Random().ints(1, 100, 255).findFirst().getAsInt();
		Integer g = new Random().ints(1, 100, 200).findFirst().getAsInt();
		Integer b = new Random().ints(1, 100, 200).findFirst().getAsInt();
		logger.info("r :"+r+" :: g :"+g+" :: b:"+b);
		Color backGroundColor = new Color(r, g, b);
		
		return backGroundColor;
	}
	
	
	public InputStream convertTexttoImageStream(ImageInfo imageInfo)
	{
		BufferedImage bImage =new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2d = bImage.createGraphics();
		
		Font font = new Font(imageInfo.getFontStyle(), Font.PLAIN, 30);
		g2d.setFont(font);
		
		FontMetrics fontMetrics = g2d.getFontMetrics(font);
		
		int width = fontMetrics.stringWidth(imageInfo.getText());
		int height = fontMetrics.getHeight();
		g2d.dispose();
		logger.info("width :"+width+" :: height :"+height);
		
		bImage = new BufferedImage(width+10+(width*(35/100)), height+10+(height*(35/100)), BufferedImage.TYPE_INT_RGB);
		g2d = bImage.createGraphics();
		g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
        g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g2d.setFont(font);
        Color backGroundColor = prepareBackGroundColor();
        Color fontColor = prepareFontColor();
        fontMetrics = g2d.getFontMetrics();
        g2d.setColor(backGroundColor);
        g2d.fillRect(0, 0, bImage.getWidth(), bImage.getHeight());
        g2d.setColor(fontColor);
        g2d.drawString(imageInfo.getText(), 5, height-(height*(10/100)));
		g2d.dispose();
		
		ByteArrayOutputStream baOs = new ByteArrayOutputStream();
		InputStream is = null;
		try {
			ImageIO.write(bImage, "png", baOs);
			is = new ByteArrayInputStream(baOs.toByteArray());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return is;
	}
}
