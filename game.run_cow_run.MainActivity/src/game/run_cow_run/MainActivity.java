package game.run_cow_run;

import jayvee.cow.CowData;
import jayvee.cow.CowLogic;
import jayvee.cow.CowView;
import jayvee.drawbox.Drawing_Data;
import jayvee.drawbox.Drawing_Logic;
import jayvee.drawbox.Drawing_Queue;
import android.os.Bundle;
import android.os.Message;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import fyy.ygame_frame.base.YAGameLogic;
import fyy.ygame_frame.base.YDomainBroadcast;
import fyy.ygame_frame.base.YGameActivity;
import fyy.ygame_frame.base.YGameView;
import fyy.ygame_frame.extra.YRequest;
import fyy.ygame_perspective.YPerspective;
import fyy.ygame_perspective.YPerspectivesConstant;
import fyy.ygame_perspective.YPerspectivesData;
import fyy.ygame_perspective.YPerspectivesLogic;
import fyy.ygame_perspective.YPerspectivesView;

public class MainActivity extends YGameActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
//        YAMapData mapData = new YMapData_JsonImp(123 ,getResources(), "ddfaw.json");
//        final YMapLogic mapLogic = new YMapLogic(mapData);
//        YAMapView mapView = YAMapView.createSimpleHorizonMap(mapData, R.drawable.item);
//        mapView.setPerspectivePic(R.drawable.bkg);
//        new YDomainBroadcast(mapData , mapView , mapLogic);
        
//        mapLogic.setStepByStepScroll(false);
        //背景图的滚动
		float fV = 0.0015f;
		YPerspective perspective1 = new YPerspective(R.drawable.bkg1, fV, 0.4f, 0, 0);
		YPerspective perspective2 = new YPerspective(R.drawable.bkg2, fV * 2, 0.4f, 0, 0.2f);
		YPerspective perspective3 = new YPerspective(R.drawable.bkg3, fV* 3, 0.6f, 0, 0.2f);
		YPerspective perspective4 = new YPerspective(R.drawable.bkg4, fV* 4, 0.6f, 0, 0.5f);
		YPerspectivesData mapData = new YPerspectivesData(123, perspective1, perspective2 , perspective3,perspective4);
		
//		YPerspective perspective = new YPerspective(R.drawable.p3, 0.5f, 1, 0, 0);
		//YPerspectivesData mapData = new YPerspectivesData(123, perspective2);
		
		final YPerspectivesLogic mapLogic = new YPerspectivesLogic(mapData);
		YPerspectivesView mapView = new YPerspectivesView(mapData);
		new YDomainBroadcast(mapData, mapLogic, mapView);
        
		
        YGameView gameView = (YGameView) findViewById(R.id.GameView);
        YAGameLogic gameLogic = gameView.getGameLogic();
        gameLogic.setUpdatePeriod(49);
        CowData cd = new CowData(222);
        final CowLogic cl = new CowLogic( cd );
        CowView cv = new CowView(cd);
        new YDomainBroadcast(cd,cl,cv);
        Drawing_Data dd = new Drawing_Data(444);
        Drawing_Logic dl = new Drawing_Logic(dd ,cl);
        Drawing_Queue dq = new Drawing_Queue(dd);
        
       
        
//        gameLogic.addDomainLogic(mapLogic,cl,dl);
//        gameView.addDomainView(mapView,cv,dq);
        gameLogic.addDomainLogic(mapLogic,dl,cl);
      gameView.addDomainView(mapView,dq,cv);
        
        //按键的设置///
        Button btn_test =(Button) findViewById(R.id.btn_test);
        btn_test.setOnClickListener(new OnClickListener() {
			
				private YRequest request = new YRequest(123,
						YPerspectivesConstant.RequestKey.LEFT_SCROLL, mapLogic);

				// private boolean b
				public void onClick(View v)
				{
					Message msg = Message.obtain();
					msg.obj = request;
					handlerGameLogic.sendMessage(msg);
				}
			
		});
        
        Button btn_jump = (Button) findViewById(R.id.btn_jump);
       btn_jump.setOnTouchListener(new OnTouchListener() {
		
		public boolean onTouch(View arg0, MotionEvent arg1) {
			// TODO Auto-generated method stub
			if(arg1.getAction() == MotionEvent.ACTION_DOWN)
			{
				Message msg = Message.obtain();
		     	msg.obj = new YRequest(111, CowLogic.iJump, cl);
				handlerGameLogic.sendMessage(msg);
			}
			return false;
		}
	});
        
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
}
