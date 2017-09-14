package com.eyck.fxreading.utils.tool;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.widget.LinearLayout;

import com.eyck.fxreading.utils.NetUtil;
import com.eyck.fxreading.utils.PaintViewUtil;
import com.orhanobut.logger.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by Eyck on 2017/9/13.
 *
 * When I worte this,only God and I understood what I was doing
 * Now,only God knows.
 */

public class AnalysisHTML {
    public String HTML_STRING = "string";
    public String HTML_URL = "url";
    private Activity context;
    private Document doc;
    private int forwardLen;
    private LinearLayout view;
    private int r;
    private int g;
    private int b;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            parseDocument(doc);
        }
    };
    private SpannableStringBuilder spannableStringBuilder;
    private int viewType;
    private PaintViewUtil paintViewUtil ;
    private StyleSpan span;
    private int wordsLength;
    private String imgUrl;
    private String imgWidth;
    private String imgHeight;
    private int space = 10;


    private void loadHtmlString(final String paramString){
        NetUtil.getGloalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                doc = Jsoup.parseBodyFragment(paramString);
                handler.sendEmptyMessage(0);
            }
        });
    }

    private void loadHtmlUrl(final String paramString) {
        NetUtil.getGloalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    doc = Jsoup.connect(paramString).get();
                    handler.sendEmptyMessage(0);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void parseChildOfPH(Element paramElement) {
        String s = paramElement.text().replaceAll("br;", "\n");
        if(!TextUtils.isEmpty(s)) {
            spannableStringBuilder = new SpannableStringBuilder("\n" + s);
            if(paramElement.nodeName().equals("h1")) {
                viewType = 1;
            }else if (paramElement.nodeName().equals("h2")) {
                viewType = 2;
            } else if (paramElement.nodeName().equals("h3")) {
                viewType = 3;
            } else if (paramElement.nodeName().equals("h4")) {
                viewType = 4;
            } else if (paramElement.nodeName().equals("h5")) {
                viewType = 5;
            } else if (paramElement.nodeName().equals("h6")) {
                viewType = 6;
            } else if (paramElement.nodeName().equals("block")) {
                viewType = 7;
            } else if (paramElement.nodeName().equals("poetry")) {
                viewType = 8;
            } else if (paramElement.nodeName().equals("hr")) {
                this.viewType = 10;
            } else {
                viewType = 0;
                if (paramElement.nodeName().contains("strong")){
                    viewType=11;
                }
                spannableStringBuilder = new SpannableStringBuilder("\n"+setFirstLineSpace(s,2));

            }
            paintViewUtil.addTypeView(context, this.view, this.viewType, spannableStringBuilder, null, null, null, this.wordsLength, 4 * space);
        }
    }

    private String setFirstLineSpace(String str,int paramInt) {
        for (int i = paramInt; i >= 0; i--) {
            str = "  " + str;
        }
        return str;
    }



    private void parseDocument(Document doc) {
        this.wordsLength = this.forwardLen;
        paintViewUtil = new PaintViewUtil(context);
        Iterator localIterator = doc.getAllElements().iterator();
        while(localIterator.hasNext()) {
            Element localElement = (Element) localIterator.next();
            Logger.e("localElement="+localElement);
            if ((localElement.nodeName().matches("p[1-9]?[0-9]?")) || (localElement.nodeName().matches("h[1-9]?[0-9]?")) || (localElement.nodeName().matches("poetry")) || (localElement.nodeName().matches("block"))) {
                parseChildOfPH(localElement);
            }
            if (localElement.nodeName().matches("img")) {
                viewType = 9;
                imgUrl = localElement.attr("src");
                if (this.imgUrl.isEmpty())
                    imgUrl = localElement.attr("href");
                if (!TextUtils.isEmpty(localElement.attr("jump_url")))
                    spannableStringBuilder = new SpannableStringBuilder(localElement.attr("jump_url"));
                this.imgWidth = localElement.attr("width");
                this.imgHeight = localElement.attr("height");
                this.paintViewUtil.addTypeView(context, view, viewType, spannableStringBuilder, imgWidth, imgHeight, imgUrl, 0, 0);
            }
        }
    }

    private SpannableStringBuilder setTextSpan(int paramInt1, int paramInt2, int paramInt3) {
        if (paramInt1 < 0)
            paramInt1 = 0;
        if (paramInt2 > this.spannableStringBuilder.length())
            paramInt2 = this.spannableStringBuilder.length();
        this.span = new StyleSpan(paramInt3);
        this.spannableStringBuilder.setSpan(this.span, paramInt1, paramInt2, 33);
        return this.spannableStringBuilder;
    }

    public int getRandomColor() {
        Random localRandom = new Random();
        this.r = (200 + localRandom.nextInt(50));
        this.g = (200 + localRandom.nextInt(50));
        this.b = (200 + localRandom.nextInt(50));
        return Color.rgb(this.r, this.g, this.b);
    }

    public void loadHtml(Activity paramActivity, String content, String type, LinearLayout paramLinearLayout, int paramInt) {
        this.context = paramActivity;
        this.view = paramLinearLayout;
        this.forwardLen = paramInt;
        String str = content.replaceAll("<br/>", "br;");
        if (type.equals(this.HTML_URL)) {
            loadHtmlUrl(str);
        } else if (type.equals(this.HTML_STRING)) {
            loadHtmlString(str);
        }
    }
}
