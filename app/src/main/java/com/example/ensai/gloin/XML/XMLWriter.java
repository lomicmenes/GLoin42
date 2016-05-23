package com.example.ensai.gloin.XML;

import android.util.Log;
import android.util.Xml;

import com.example.ensai.gloin.Image;

import org.xmlpull.v1.XmlSerializer;

import java.io.StringWriter;

/**
 * Created by ensai on 22/05/16.
 */
public class XMLWriter {

    public static String writeUsingXMLSerializer(Image image) throws Exception {
        XmlSerializer xmlSerializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();

        xmlSerializer.setOutput(writer);
        // start DOCUMENT
        xmlSerializer.startDocument("UTF-8", true);
        // open tag: <image>
        xmlSerializer.startTag("", Image.IMAGE);
        xmlSerializer.attribute("", Image.NAME, image.getName());
        xmlSerializer.attribute("", Image.SELLER, image.getSeller());

        // open tag: <business-plan>
        xmlSerializer.startTag("", Image.BUSINESS_PLAN);
        xmlSerializer.attribute("", Image.PROFIT, String.valueOf(image.getProfit()));
        xmlSerializer.attribute("", Image.MIN_PRICE, String.valueOf(image.getMinPrice()));
        xmlSerializer.attribute("", Image.MAX_PRICE, String.valueOf(image.getMaxPrice()));

        // close tag <business-plan>
        xmlSerializer.endTag("", Image.BUSINESS_PLAN);

        // open tag: <status>
        xmlSerializer.startTag("", Image.STATUS);
        xmlSerializer.attribute("", Image.CURRENT_PRICE, String.valueOf(image.getCurrentPrice()));
        xmlSerializer.attribute("", Image.NB_BUYER, String.valueOf(image.getNbBuyer()));
        xmlSerializer.attribute("", Image.DUE_TO_SELLER, String.valueOf(image.getDueToSeller()));

        // close tag: </status>
        xmlSerializer.endTag("", image.STATUS);

        // close tag: </image>
        xmlSerializer.endTag("", Image.IMAGE);

        // end DOCUMENT
        xmlSerializer.endDocument();

        String result = writer.toString().replace('/',Character.MIN_VALUE);
        Log.d("XML", "Mon joli XML : " + result);

        return result;
    }
}
