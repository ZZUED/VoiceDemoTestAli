package com.alibaba.idst.nlsdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.idst.R;
import com.alibaba.idst.nls.NlsClient;
import com.alibaba.idst.nls.NlsListener;
import com.alibaba.idst.nls.StageListener;
import com.alibaba.idst.nls.internal.protocol.NlsRequest;
import com.alibaba.idst.nls.internal.protocol.NlsRequestProto;

public class PublicAsrActivity extends Activity {

    private boolean isRecognizing = false;
    private EditText mFullEdit;
    private EditText mResultEdit;
    private Button mStartButton;
    private Button mStopButton;
    private NlsClient mNlsClient;
    private NlsRequest mNlsRequest;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_asr);
        context = getApplicationContext();
        mFullEdit = (EditText) findViewById(R.id.editText2);
        mResultEdit = (EditText) findViewById(R.id.editText);
        mStartButton = (Button) findViewById(R.id.button);
        mStopButton = (Button) findViewById(R.id.button2);

        String appkey = "nls-service"; //请设置申请到的Appkey
        mNlsRequest = initNlsRequest();
        mNlsRequest  = initNlsRequest();
        mNlsRequest.setApp_key(appkey);    //appkey请从 "快速开始" 帮助页面的appkey列表中获取
        mNlsRequest.setAsr_sc("opu");      //设置语音格式
        
        /*设置热词相关属性*/
        //mNlsRequest.setAsrVocabularyId("vocabid");
        /*设置热词相关属性*/

        NlsClient.openLog(true);
        NlsClient.configure(getApplicationContext()); //全局配置
        mNlsClient = NlsClient.newInstance(this, mRecognizeListener, mStageListener,mNlsRequest);                          //实例化NlsClient

        mNlsClient.setMaxRecordTime(60000);  //设置最长语音
        mNlsClient.setMaxStallTime(1000);    //设置最短语音
        mNlsClient.setMinRecordTime(500);    //设置最大录音中断时间
        mNlsClient.setRecordAutoStop(false);  //设置VAD
        mNlsClient.setMinVoiceValueInterval(200); //设置音量回调时长

        initStartRecognizing();
        initStopRecognizing();
    }

    private NlsRequest initNlsRequest(){
        NlsRequestProto proto = new NlsRequestProto(context);
        //proto.setApp_user_id(""); //设置在应用中的用户名，可选
        return new NlsRequest(proto);

    }

    private void initStartRecognizing(){
        mStartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRecognizing = true;
                mResultEdit.setText("正在录音，请稍候！");
                mNlsRequest.authorize("", ""); //请替换为用户申请到的数加认证key和密钥
                mNlsClient.start();
                mStartButton.setText("录音中。。。");
            }
        });
    }

    private void initStopRecognizing(){
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isRecognizing = false;
                mResultEdit.setText("");
                mNlsClient.stop();
                mStartButton.setText("开始 录音");

            }
        });
    }

    private NlsListener mRecognizeListener = new NlsListener() {

        @Override
        public void onRecognizingResult(int status, RecognizedResult result) {
            switch (status) {
                case NlsClient.ErrorCode.SUCCESS:
                    Log.i("asr", "[demo]  callback onRecognizResult " + result.asr_out);
                    mResultEdit.setText(result.asr_out);
                    mFullEdit.setText(result.asr_out);
                    break;
                case NlsClient.ErrorCode.RECOGNIZE_ERROR:
                    Toast.makeText(PublicAsrActivity.this, "recognizer error", Toast.LENGTH_LONG).show();
                    break;
                case NlsClient.ErrorCode.RECORDING_ERROR:
                    Toast.makeText(PublicAsrActivity.this,"recording error",Toast.LENGTH_LONG).show();
                    break;
                case NlsClient.ErrorCode.NOTHING:
                    Toast.makeText(PublicAsrActivity.this,"nothing",Toast.LENGTH_LONG).show();
                    break;
            }
            isRecognizing = false;
        }


    } ;

    private StageListener mStageListener = new StageListener() {
        @Override
        public void onStartRecognizing(NlsClient recognizer) {
            super.onStartRecognizing(recognizer);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onStopRecognizing(NlsClient recognizer) {
            super.onStopRecognizing(recognizer);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onStartRecording(NlsClient recognizer) {
            super.onStartRecording(recognizer);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onStopRecording(NlsClient recognizer) {
            super.onStopRecording(recognizer);    //To change body of overridden methods use File | Settings | File Templates.
        }

        @Override
        public void onVoiceVolume(int volume) {
            super.onVoiceVolume(volume);
        }

    };

}
