package com.supermap.android.spatialanalyst;

import java.text.DecimalFormat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;


import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;

import com.supermap.data.Workspace;
import com.supermap.mapping.MapView;
import com.supermap.mapping.MapControl;
import com.supermap.analyst.BufferAnalystGeometry;
import com.supermap.analyst.BufferAnalystParameter;
import com.supermap.analyst.BufferEndType;
import com.supermap.analyst.R;
import com.supermap.analyst.networkanalyst.TransportationAnalyst;
import com.supermap.analyst.networkanalyst.TransportationAnalystParameter;
import com.supermap.analyst.networkanalyst.TransportationAnalystResult;
import com.supermap.analyst.networkanalyst.TransportationAnalystSetting;
import com.supermap.analyst.networkanalyst.WeightFieldInfo;
import com.supermap.analyst.networkanalyst.WeightFieldInfos;
import com.supermap.analyst.spatialanalyst.OverlayAnalyst;
import com.supermap.analyst.spatialanalyst.OverlayAnalystParameter;
import com.supermap.android.app.MyApplication;
import com.supermap.data.Color;
import com.supermap.data.CoordSysTransMethod;
import com.supermap.data.CoordSysTransParameter;
import com.supermap.data.CoordSysTranslator;
import com.supermap.data.CursorType;
import com.supermap.data.DatasetVector;
import com.supermap.data.DatasetVectorInfo;
import com.supermap.data.Datasets;
import com.supermap.data.Datasource;
import com.supermap.data.Datasources;
import com.supermap.data.EncodeType;
import com.supermap.data.GeoLine;
import com.supermap.data.GeoLineM;
import com.supermap.data.GeoRegion;
import com.supermap.data.GeoStyle;
import com.supermap.data.Geometry;
import com.supermap.data.GeometryType;
import com.supermap.data.Point;
import com.supermap.data.Point2D;
import com.supermap.data.Point2Ds;
import com.supermap.data.PrjCoordSys;
import com.supermap.data.PrjCoordSysType;
import com.supermap.data.Recordset;
import com.supermap.data.StatisticMode;
import com.supermap.mapping.CallOut;
import com.supermap.mapping.CalloutAlignment;
import com.supermap.mapping.LayerSettingVector;
import com.supermap.mapping.Map;
import com.supermap.mapping.TrackingLayer;

/**
 * <p>
 * Title:�ռ����
 * </p>
 * 
 * <p>
 * Description:
 * ============================================================================>
 * ------------------------------��Ȩ����----------------------------
 * ���ļ�Ϊ SuperMap iMobile ��ʾDemo�Ĵ��� 
 * ��Ȩ���У�������ͼ����ɷ����޹�˾
 * ----------------------------------------------------------------
 * ----------------------------SuperMap iMobile ��ʾDemo˵��---------------------------
 * 
 * 1��Demo��飺
 *   չʾ��ζ�ָ����·�����л����������͵��ӷ�����
 * 2��Demo���ݣ�����Ŀ¼��"/SuperMap/Demos/Data/SpatialAnalystData/"
 *           ��ͼ���ݣ�"Changchun.smwu", "Changchun.udb", "Changchun.udd"
 *           ���Ŀ¼��"/SuperMap/License/" 
 * 3���ؼ�����/��Ա: 
 *		TransportationAnalystSetting.setNetworkDataset();	       ����
 *		TransportationAnalystSetting.setEdgeIDField();		       ����
 *		TransportationAnalystSetting.setNodeIDField();		       ����
 *		TransportationAnalystSetting.setEdgeNameField();	       ����
 *		TransportationAnalystSetting.setWeightFieldInfos();	        ����
 *		TransportationAnalystSetting.setFNodeIDField();		        ����
 *		TransportationAnalystSetting.setTNodeIDField();		        ����
 *		TransportationAnalyst.setAnalystSetting();			         ����
 *		TransportationAnalyst.load();						         ����
 *         
 *		TransportationAnalystParameter.setPoints();			         ����
 *		TransportationAnalystParameter.setNodesReturn();	         ����
 *		TransportationAnalystParameter.setEdgesReturn();	         ����
 *		TransportationAnalystParameter.setPathGuidesReturn();  ����
 *		TransportationAnalystParameter.setRoutesReturn();	          ����
 *		TransportationAnalyst.findPath();					          ����
 *      TransportationAnalystResult.getRoutes();               ����
 *      BufferAnalystParameter.setLeftDistance();              ����
 *		BufferAnalystParameter.setRightDistance();             ����
 *		BufferAnalystParameter.setEndType();                   ����
 *      BufferAnalystGeometry.createBuffer();                  ����
 *      TrackingLayer.add();                                   ����
 *      
 * 4������չʾ
 *   (1)��������֮�����·����
 *   (2)������������
 *   (3)���ӷ�����
 * ------------------------------------------------------------------------------
 * ============================================================================>
 * </p> 
 * 
 * <p>
 * Company: ������ͼ����ɷ����޹�˾
 * </p>
 * 
 */
@SuppressLint("SdCardPath")
public class MainActivity extends Activity {
	
	// ���尴ť�ؼ�
    private ImageButton      btn_analyse_path     = null;
    private ImageButton      btn_clean            = null;
    private ImageButton      btn_create_buffer    = null;
    private ImageButton      btn_end_point        = null;
    private ImageButton      btn_entire           = null;
    private ImageButton      btn_overlay_analyst  = null;
    private ImageButton      btn_path_analyst     = null;
    private ImageButton      btn_start_point      = null;
    private ImageButton      btn_zoomIn           = null;
    private ImageButton      btn_zoomOut          = null;
    private ImageButton      btn_setting          = null;      // �������뾶���ð�ť��������ʾ�����ػ������뾶�����ı���
    
    // �����ı��ؼ�
    private TextView         areaOverlayView      = null;      // ��ʾ���ӷ���Ӱ�����
    private EditText         bufferRadiusText     = null;      // �������뾶�����ı���
    
    // ���岼�ֿؼ�
	private RelativeLayout    m_FrameLayout00     = null;      // �������뾶���ò���
	private FrameLayout    m_FrameLayout01        = null;      // ���ӷ���Ӱ���������
	private LinearLayout   m_PathAnalystDropDown  = null;      // ·����������㡢�յ����ð�ť����
	
	// �����ͼ�ؼ�
	private MapView     m_mapView     = null;
	private Workspace   m_workspace   = null;
	private MapControl  m_mapControl  = null;
	private Map         m_Map         = null;
	
	// �������������ͼ�ؼ�    	
	private Point2Ds       m_Point2Ds       = null;
	private TrackingLayer  m_TrackingLayer  = null;
		
	// �������ݿռ�	
	private Datasources   m_Datasources = null;
	private Datasource    m_Datasource  = null;
	private Datasets      m_Datasets    = null;
	private DatasetVector m_DatasetLine = null;

	
	// ����ռ�����ռ�
	private TransportationAnalyst        m_TransAnalyst        = null;
	private TransportationAnalystResult  m_TransAnalystResult  = null;
	
	
	// �����ַ�������
	private String  m_nodeID           = "SmNodeID";
	private String  m_edgeID           = "SmEdgeID";
	private String  roadDatasetName    = "RoadNet";
	private String  resultDatasetName  = "resultDatasetClip";
	
	// ���岼������
	private boolean isStartPoint       = false;
	private boolean isEndPoint         = false;
	private boolean isLongPressEnable  = false;
	private boolean isExitEnable       = false;
	// �������ͱ���
	private int    dataSourceIndex = 0;
	private double clipArea        = 0;
	private int    bufferRadius    = 30;                          // ���û������뾶Ĭ��ֵ

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
				
		setContentView(R.layout.activity_main);
		
		boolean  isOpenMap = false;
		isOpenMap = initMap();                                    // �򿪹����ռ䣬�򿪵�ͼ 
		if(isOpenMap){
		    initAnalystEnvironment();                             // ��ʼ�����ռ�����������Դ����
		    initView();                                           // ��ʼ������ؼ�
		}else{
			showInfo("Initialize Map failed.");
			System.out.println("Initialize Map failed.");
		}
		
	}
	
	/**
	 * �򿪹����ռ䣬��ʾ��ͼ
	 * @return
	 */
	public boolean initMap() {
        boolean isOpen = false;
		
        // ��ȡ��ͼ�ؼ�
		m_mapView   = (MapView) findViewById(R.id.mapView);
		m_mapControl = m_mapView.getMapControl();
		
		// ��һ�������ռ�
		m_workspace = new Workspace();
		
		m_workspace = MyApplication.getInstance().getOpenedWorkspace();
		// ������ͼ�ؼ��͹����ռ�
		m_mapControl.getMap().setWorkspace(m_workspace);
		
		// �򿪵�ͼ
		isOpen = m_mapControl.getMap().open(m_workspace.getMaps().get(0));   // Ĭ�ϴ򿪵�һ����ͼ
		if(!isOpen)
		{
			System.out.println("Open map failed.");
			showInfo("Open map failed. ");
			
			return false;
		}
		m_mapControl.getMap().setFullScreenDrawModel(true);
		m_mapControl.getMap().refresh();
		
		return true;
	}
	
	/**
	 * ��ʼ��������ؼ�
	 */
	public void initView() {
		
		// ���ó����¼�����
		m_mapControl.setGestureDetector(new GestureDetector(longTouchListener));;
		
		btn_path_analyst = (ImageButton) findViewById(R.id.btn_path_analyst);
		btn_path_analyst.setOnClickListener(new ImageButtonOnClickListener());
		
		btn_start_point = (ImageButton) findViewById(R.id.btn_start_point);
		btn_start_point.setOnClickListener(new ImageButtonOnClickListener());
		
		btn_end_point = (ImageButton) findViewById(R.id.btn_end_point);
		btn_end_point.setOnClickListener(new ImageButtonOnClickListener());
		
		btn_analyse_path = (ImageButton) findViewById(R.id.btn_analyse_path);
		btn_analyse_path.setOnClickListener(new ImageButtonOnClickListener());
		
		btn_clean = (ImageButton) findViewById(R.id.btn_clear);
		btn_clean.setOnClickListener(new ImageButtonOnClickListener());
		
		btn_create_buffer = (ImageButton) findViewById(R.id.btn_create_buffer);
		btn_create_buffer.setOnClickListener(new ImageButtonOnClickListener());

		btn_overlay_analyst = (ImageButton) findViewById(R.id.btn_overlay_analyst);
		btn_overlay_analyst.setOnClickListener(new ImageButtonOnClickListener());
		
		btn_setting = (ImageButton) findViewById(R.id.btn_setting);
		btn_setting.setOnClickListener(new ImageButtonOnClickListener());

		
		
		btn_entire = (ImageButton) findViewById(R.id.btn_entire);
		btn_entire.setOnClickListener(new ImageButtonOnClickListener());
		
		btn_zoomOut = (ImageButton) findViewById(R.id.btn_zoomOut);
		btn_zoomOut.setOnClickListener(new ImageButtonOnClickListener());
		
		btn_zoomIn = (ImageButton) findViewById(R.id.btn_zoomIn);
		btn_zoomIn.setOnClickListener(new ImageButtonOnClickListener());
		
		m_PathAnalystDropDown = (LinearLayout) findViewById(R.id.PathAnalyst_DropDown);
		
		m_FrameLayout01   = (FrameLayout) findViewById(R.id.frameLayout01);
		areaOverlayView = (TextView) findViewById(R.id.areaOverlayView);
		
		m_FrameLayout00   = (RelativeLayout) findViewById(R.id.frameLayout00);
		bufferRadiusText   = (EditText) findViewById(R.id.bufferRadiusText);

		bufferRadiusText.addTextChangedListener(textWatcher);
	}
	
	// ����EditText������ı仯������ȡ�������뾶ֵ
	@SuppressLint("NewApi") private TextWatcher textWatcher = new TextWatcher(){
		
		@Override
		public void afterTextChanged(Editable s)
		{			
			
			String digitStr = bufferRadiusText.getText().toString();
			if(digitStr.length()>=1 && digitStr.length()<=bufferRadiusText.getMaxHeight()){
			  bufferRadius = Integer.valueOf(bufferRadiusText.getText().toString()); 
			  
			}
			if(digitStr.length() ==bufferRadiusText.getMaxHeight()){
				showInfo("���ð뾶�ﵽ���ֵ");
			}
		}

	
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			// TODO Auto-generated method stub
			
		}

		
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub
			
		}
	
	};
	
	// ��ť����¼�����
    private class ImageButtonOnClickListener implements OnClickListener {
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_path_analyst:               // ���ں���·���������ý���
				
				// �����㡢�յ����ð�ťδ��ʾ������ʾ����֮����
				if(m_PathAnalystDropDown.getVisibility() == View.VISIBLE){
				    btn_path_analyst.setBackgroundResource(R.drawable.bg_white_round);
				    m_PathAnalystDropDown.setVisibility(View.GONE);
				    
				}else{
					btn_path_analyst.setBackgroundResource(R.drawable.bg_white_top_round);
					m_PathAnalystDropDown.setVisibility(View.VISIBLE);
									
				}
				break;
				
			case R.id.btn_start_point:
				
				// �����յ����õĻ����жϣ�����������������յ�ǰ�������Կ�ʼ������һ����
				if(isLongPressEnable){
					showInfo("��������յ������");
				}else{
			      	btn_start_point.setEnabled(false);
			  	    isStartPoint = true;
				    isLongPressEnable = true;
				    showInfo("�볤���������");
				}
				
				break;
				
			case R.id.btn_end_point:
				
				if(isLongPressEnable){
					showInfo("���������������");
				}else{
				    btn_end_point.setEnabled(false);
				    isEndPoint = true;
				    isLongPressEnable = true;
				
				    showInfo("�볤�������յ�");
				}
				
				break;
				
			case R.id.btn_analyse_path:
				// ��������յ����ú���ܽ���·������
				if(m_Point2Ds.getCount()<2)
				{
					showInfo("�����������յ�");
					
				}else{
				    btn_analyse_path.setEnabled(false);
				    				    			
				    startPathAnalyse();                             // ��ʼ·������	

				}
				break;
				
			case R.id.btn_clear:
					
				// ������������ע�⻺�����뾶��ǰһ�������ֵһ��
				m_mapView.removeAllCallOut();
				
				m_Point2Ds.clear();
								
				boolean isContained = m_Datasets.contains(resultDatasetName);
				if(isContained){                                     // ����з���������ݼ�����ɾ��
					m_Map.getLayers().remove(0);
					m_Datasets.delete(resultDatasetName);
					
				}
				if(m_TrackingLayer.getCount()>0){
					m_TrackingLayer.clear();
				}

				// ���ز��ְ�ť
				btn_path_analyst.setBackgroundResource(R.drawable.bg_white_round);				
				m_PathAnalystDropDown.setVisibility(View.GONE);
				btn_create_buffer.setVisibility(View.GONE);
				btn_overlay_analyst.setVisibility(View.GONE);

				// �����ı���
				m_FrameLayout01.setVisibility(View.GONE);
				
				m_FrameLayout00.setVisibility(View.GONE);								
				
				// ���ð�������
				btn_path_analyst.setEnabled(true);
				btn_analyse_path.setEnabled(true);
				btn_start_point.setEnabled(true);
				btn_end_point.setEnabled(true);
				btn_create_buffer.setEnabled(true);
				btn_overlay_analyst.setEnabled(true);
				btn_setting.setEnabled(true);
				
				isLongPressEnable = false;								
				clipArea = 0;
				m_Map.refresh();
				break;
			case R.id.btn_create_buffer:
				btn_create_buffer.setEnabled(false);

				// ��ʼ����������ʱ������·���������ð�ť
				m_PathAnalystDropDown.setVisibility(View.GONE);
				m_FrameLayout00.setVisibility(View.GONE);

				startBufferAnalyse();
				break;
			
            case R.id.btn_overlay_analyst:
            	btn_overlay_analyst.setEnabled(false);
            	
            	startOverlayAnalyse();
            	           	
				break;

            case R.id.btn_setting:				
            	// �����Ƿ���ʾ����뾶�����
            	if(m_FrameLayout00.getVisibility() == View.VISIBLE)
            	{
            		m_FrameLayout00.setVisibility(View.GONE);
            	}else{
            	    m_FrameLayout00.setVisibility(View.VISIBLE);
            	}
				break;
			
			case R.id.btn_entire:
				m_mapControl.getMap().viewEntire();
				m_mapControl.getMap().refresh();
				
				break;
				
			case R.id.btn_zoomOut:
				m_mapControl.getMap().zoom(0.5);
				m_mapControl.getMap().refresh();
				
				break;
				
			case R.id.btn_zoomIn:
				m_mapControl.getMap().zoom(2);
				m_mapControl.getMap().refresh();
				
				break;

			default:
				break;
			}
		}
	}
    
    // �����¼�����
    SimpleOnGestureListener longTouchListener = new SimpleOnGestureListener() {
    	
    	public void onLongPress(MotionEvent event) {
    		if(isLongPressEnable){                                            //  �����ȡ�����¼����ʱ����Ӧ
    		        getPoints(event, isStartPoint, isEndPoint);               //  ��ȡ�������õĵ�����꣬��ת��Ϊ��γ����
    		     isLongPressEnable = false;                                   //  ����һ�����ʹ������Ӧ��Ч
    		     
    		     // �ж����õ�����㻹���յ�
    		     if(isStartPoint) 
    		    	 isStartPoint  = false;
    		     if(isEndPoint)   
    		    	 isEndPoint    = false;
    		}
    	}
    };
    
    /**
     * ��ȡ��Ļ�ϵĵ㣬��ת���ɵ�ͼ����
     * @param event
     * @param bStartPoint
     * @param bEndPoint
     */
    public void getPoints(MotionEvent event , boolean bStartPoint, boolean bEndPoint) {
    	
    	//��ȡ��Ļ�ϵĵ�����ĵ�����(x, y)
    	int x = (int) event.getX();
    	int y = (int) event.getY();
    	isStartPoint = bStartPoint;
    	isEndPoint   = bEndPoint;
    	
    	// ת��Ϊ��ͼ��ά��
    	Point2D point2D = m_Map.pixelToMap(new Point(x, y));
    	
    	// ���ñ�ע
    	CallOut callOut = new CallOut(MainActivity.this);
    	callOut.setStyle(CalloutAlignment.BOTTOM);             // ���ñ�ע��Ķ��뷽ʽ���·�����
    	callOut.setCustomize(true);                            // �����Զ��屳��
    	callOut.setLocation(point2D.getX(), point2D.getY());   // ���ñ�ע������
    	
    	// ͶӰת����ת��Ϊ��γ����ϵ
    	if(m_Map.getPrjCoordSys().getType() != PrjCoordSysType.PCS_EARTH_LONGITUDE_LATITUDE){
    		Point2Ds point2Ds = new Point2Ds();
    		point2Ds.add(point2D);
    		PrjCoordSys destPrjCoordSys = new PrjCoordSys();
    		// ����Ŀ������ϵ����
    		destPrjCoordSys.setType(PrjCoordSysType.PCS_EARTH_LONGITUDE_LATITUDE);
    		// ��ȡ��ǰ��ͼ����ϵ
    		PrjCoordSys sourPrjCoordSys = m_Map.getPrjCoordSys();
    		// ת��ͶӰ����
    		CoordSysTranslator.convert(point2Ds, sourPrjCoordSys, destPrjCoordSys, new CoordSysTransParameter(), CoordSysTransMethod.MTH_GEOCENTRIC_TRANSLATION);
    		
    		point2D = point2Ds.getItem(0);
    	}
    	
    	ImageView imageView = new ImageView(MainActivity.this);
    	
    	// ��ӵ㵽��
    	if(isStartPoint && !isEndPoint) {
    		// ��ʾ���
    		imageView.setBackgroundResource(R.drawable.start_point);
    		callOut.setContentView(imageView);
    		m_mapView.addCallout(callOut);
    		
    		// ������
    		m_Point2Ds.add(point2D);
    		
    		isStartPoint = false;
    		
    	}else if(!isStartPoint && isEndPoint){
    		// ��ʾ�յ�
    		imageView.setBackgroundResource(R.drawable.end_point);
    		callOut.setContentView(imageView);
    		m_mapView.addCallout(callOut);
    		
    		// ����յ�
    		m_Point2Ds.add(point2D);
    		    	
    		isEndPoint = false;
    		
    	}else{
    		
    	}
    }

	/**
	 * ��ʼ����ͼ�ؼ������ݶ��󣬷����ؼ�, ���价��
	 */
	private void initAnalystEnvironment() {

		m_Datasources = m_workspace.getDatasources(); // ��ȡ����Դ����
		m_Datasource = m_Datasources.get(dataSourceIndex); // Ĭ�ϻ�ȡ��һ������Դ
		m_Datasets = m_Datasource.getDatasets(); // ��ȡ���ݼ�����
		m_DatasetLine = (DatasetVector) m_Datasets.get(roadDatasetName); // ��ȡ��·���ݼ�
		m_Map = m_mapControl.getMap();
		m_TrackingLayer = m_Map.getTrackingLayer(); // ��ȡ��ͼ�ĸ��ٲ�
		m_Point2Ds = new Point2Ds();

		initNetworkAnaystEnvironment();
	}

	/**
	 * ��ʼ�������������
	 */
	public void initNetworkAnaystEnvironment() {

		// ���岢��ʼ�����������������
		TransportationAnalystSetting transAnalystSetting = new TransportationAnalystSetting();
		transAnalystSetting.setNetworkDataset(m_DatasetLine);
		transAnalystSetting.setEdgeIDField(m_edgeID);
		transAnalystSetting.setNodeIDField(m_nodeID);
		transAnalystSetting.setEdgeNameField("roadName");
		transAnalystSetting.setTolerance(89);

		WeightFieldInfos weightFieldInfos = new WeightFieldInfos();
		WeightFieldInfo weightFieldInfo = new WeightFieldInfo();
		weightFieldInfo.setFTWeightField("smLength");
		weightFieldInfo.setTFWeightField("smLength");
		weightFieldInfo.setName("length");
		weightFieldInfos.add(weightFieldInfo);

		transAnalystSetting.setWeightFieldInfos(weightFieldInfos);
		transAnalystSetting.setFNodeIDField("SmFNode");
		transAnalystSetting.setTNodeIDField("SmTNode");

		// ��ʼ�������������
		m_TransAnalyst = new TransportationAnalyst();
		m_TransAnalyst.setAnalystSetting(transAnalystSetting);
		m_TransAnalyst.load();

	}

	/**
	 * ��ʼ·������
	 */
	public void startPathAnalyse() {
		final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
		dialog.setCancelable(false);
		dialog.setCanceledOnTouchOutside(false);
		dialog.setMessage("·��������....");
		dialog.show();

		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub

				boolean isGetResult = analysePath();

				dialog.dismiss();
				if (isGetResult) {
					// �����������޸�������
					Runnable action = new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub

							btn_create_buffer.setVisibility(View.VISIBLE);
							m_PathAnalystDropDown.setVisibility(View.GONE);
							btn_path_analyst
									.setBackgroundResource(R.drawable.bg_white_round);
						}

					};
					MainActivity.this.runOnUiThread(action);
				} else {
					// showInfo("����ʧ��");
					// ��ʾ����ʧ����ʾ
					Runnable action = new Runnable() {

						@Override
						public void run() {
							// TODO Auto-generated method stub
							showInfo("����·��ʧ��");
						}
					};
					MainActivity.this.runOnUiThread(action);
					System.out.println("����·��ʧ�ܡ�����������");
				}
			}
		}).start();

	}
        
	/**
	 * ���·����������������յ����ú󣬿��Ե��ø÷���
	 * 
	 * @return
	 */
	public boolean analysePath() {

		// ���岢���������������
		TransportationAnalystParameter transParameter = new TransportationAnalystParameter();
		transParameter.setWeightName("length");
		transParameter.setPoints(m_Point2Ds);
		transParameter.setNodesReturn(true);
		transParameter.setEdgesReturn(true);
		transParameter.setPathGuidesReturn(true);
		transParameter.setRoutesReturn(true);
		try {
			m_TransAnalystResult = m_TransAnalyst.findPath(transParameter,
					false);

		} catch (Exception e) {
			m_TransAnalystResult = null;
		}

		if (m_TransAnalystResult == null) {
			return false;
		}
		// ��ʾ�������
		showPathAnalystResult();
		return true;
	}

	/**
	 * ��ʾ·���������
	 */
	public void showPathAnalystResult() {
		int i = 0; // ѭ������

		// ������ٲ��ϱ�ǩΪ��result���ļ��ζ���
		int count = m_TrackingLayer.getCount();

		for (i = 0; i < count; i++) {
			int index = m_TrackingLayer.indexOf("result");
			if (index != -1)
				m_TrackingLayer.remove(index);
		}

		// ��ȡ·����������е�·�ɼ���
		GeoLineM[] routes = m_TransAnalystResult.getRoutes();
		if (routes == null) {
			System.out.println("��ȡ·�ɼ���ʧ��");
			return;
		}

		for (i = 0; i < routes.length; i++) {
			GeoLineM geoLineM = routes[i];
			GeoStyle geoStyle = new GeoStyle();
			geoStyle.setLineColor(new Color(255, 90, 0));
			geoStyle.setLineWidth(1);
			geoLineM.setStyle(geoStyle);
			m_TrackingLayer.add(geoLineM, "result"); // ��ӷ�����������ٲ㣬�����ñ�ǩ
		}
		m_Map.refresh();
	}

	public void showInfo(String mesg) {
		Toast.makeText(this, mesg, Toast.LENGTH_SHORT).show();
	}
	
	/**
	 * ��ʼ����������
	 */
    public void startBufferAnalyse()
    {  	
    	if(bufferRadius <=0){
    		showInfo("�������뾶��Ч������ʧ��");
    		btn_create_buffer.setEnabled(true);
    		return;
    	}
    	final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
    	dialog.setCancelable(false);
    	dialog.setCanceledOnTouchOutside(false);
    	dialog.setMessage("������������....");
    	dialog.show();
    	   	
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				// ����������
				createBuffer();
				
				dialog.dismiss();
				// �����������޸�������
				Runnable action = new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
		            	btn_overlay_analyst.setVisibility(View.VISIBLE);		            			            	
					}
				};
				MainActivity.this.runOnUiThread(action);
			}
		}).start();
    	
    }
    
	/**
	 *  ����������
	 */
	public void createBuffer(){
		
		GeoRegion geometryBuffer = null;                                   // ���建���������������
		
		// ���û���������ζ���,�����ٲ��һ�����ζ���,������ת����GeoLine����
		int index = m_TrackingLayer.indexOf("result");
				
		Geometry geometry = m_TrackingLayer.get(index);                     // ��ȡ·���������		
		GeoLine geoLineForBuffer = ((GeoLineM)geometry).convertToLine();    // ��·�ɶ���ת�����߶���
		Geometry geoForBuffer = (Geometry)geoLineForBuffer;
		
		// ���û�������������
		BufferAnalystParameter bufferAnalystParameter = new BufferAnalystParameter();
		bufferAnalystParameter.setLeftDistance(bufferRadius);
		bufferAnalystParameter.setRightDistance(bufferRadius);
		bufferAnalystParameter.setEndType(BufferEndType.ROUND);
		
		// ����ͶӰ����ϵ		
		PrjCoordSys prjCoordSys = m_Map.getPrjCoordSys();
		  
		// ���ɻ��������ζ���
		geometryBuffer = BufferAnalystGeometry.createBuffer(geoForBuffer, bufferAnalystParameter, prjCoordSys);
		
	    // ���ü��ζ�����
		GeoStyle style = new GeoStyle();
		style.setLineColor(new Color(50, 244, 50));
		style.setLineSymbolID(0);
		style.setLineWidth(0.5);
		style.setMarkerSymbolID(351);
		style.setMarkerSize(new com.supermap.data.Size2D(5,5));
		style.setFillForeColor(new Color(147, 16, 133));
		style.setFillOpaqueRate(70);
		
		geometryBuffer.setStyle(style);            // ���û�������������ķ��
		m_TrackingLayer.clear();                   // ������ٲ���ԭ�еĽ������·���������		
		m_TrackingLayer.add(geometryBuffer, "");   // ��ӻ�������������ٲ�
		
		m_Map.refresh();
	}
	
	/**
	 * ��ʼ���ӷ���
	 */
    public void startOverlayAnalyse()
    {  	
    	final ProgressDialog dialog = new ProgressDialog(MainActivity.this);
    	dialog.setCancelable(false);
    	dialog.setCanceledOnTouchOutside(false);
    	dialog.setMessage("���ӷ����С�������");
    	dialog.show();
    	    	
    	new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				// ���ӷ���֮ �ü�
				overlayAnalystClip();
				
				dialog.dismiss();
				
				// �����������޸�������
				Runnable action = new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						// ��ʾӰ�����
		            	m_FrameLayout01.setVisibility(View.VISIBLE);
		            	DecimalFormat df = new DecimalFormat("0.000000");
		            	areaOverlayView.setText("" + df.format(clipArea));		            	
					}
				};
				MainActivity.this.runOnUiThread(action);
			}
		}).start();
    }
    
	/**
	 *  ���ӷ���֮�ü�
	 */
	public void overlayAnalystClip() {
		DatasetVector srcDataset     = null;
		Geometry[]    clipGeometries = new Geometry[1];                // ���ڴ�Ż���������������󣬽��ֻ��һ�������
		DatasetVector resultDataset  = null;
		DatasetVectorInfo resultDatasetInfo = new DatasetVectorInfo();
		
		// ��ȡ�����ü������ݼ�
		srcDataset =(DatasetVector) m_Datasource.getDatasets().get("ResidentialArea");
		// ��ȡ�����������Ľ���������뼸�ζ��󼯺��У���ȡ�ļ��ζ�������������
		int geoCount = m_TrackingLayer.getCount();
		for(int i=0, j=0; i<geoCount && j<1; i++)
		{
			if(m_TrackingLayer.get(i).getType() == GeometryType.GEOREGION)
			{
				clipGeometries[j] = m_TrackingLayer.get(i);
				j++;
			}			
		}
		// ɾ�����еķ���������ݼ�
		boolean isContained = m_Datasets.contains(resultDatasetName);
		if(isContained)
			m_Datasets.delete(resultDatasetName);
		
		
		// ���ý�����ݼ���Ϣ
		resultDatasetInfo.setType(srcDataset.getType());
		resultDatasetInfo.setName(resultDatasetName);
		resultDatasetInfo.setEncodeType(EncodeType.NONE);
		// ����������ݼ�
		resultDataset = m_Datasource.getDatasets().create(resultDatasetInfo);
		
		OverlayAnalystParameter overlayAnalystParameter = new OverlayAnalystParameter();
		overlayAnalystParameter.setTolerance(0.0112242);
		
		OverlayAnalyst.clip(srcDataset, clipGeometries, resultDataset, overlayAnalystParameter);
		
		// ��ʾ���ǰ����ո��ٲ�
		m_TrackingLayer.clear();
		
	   // ��ʾ���	
	   /***************************************************/	
		// ���ü��ζ���ķ��
		GeoStyle style = new GeoStyle();
		style.setLineColor(new Color(50, 244, 50));
		style.setLineSymbolID(0);
		style.setLineWidth(0.5);
		style.setMarkerSymbolID(351);
		style.setMarkerSize(new com.supermap.data.Size2D(5,5));
		style.setFillForeColor(new Color(244, 50, 50));
		style.setFillOpaqueRate(70);
		
		// ����ͼ����
		LayerSettingVector  m_LayerSettingVector = new LayerSettingVector();
		m_LayerSettingVector.setStyle(style);
		
		// ��ӽ�����ݼ�����ͼ�������ͼ��
        m_Map.getLayers().add(resultDataset, true);
        // ����ͼ����
		m_Map.getLayers().get(0).setAdditionalSetting(m_LayerSettingVector);
		
		m_Map.refresh();
		// ��ȡӰ�����
		Recordset recordset = resultDataset.getRecordset(false, CursorType.DYNAMIC);
		clipArea = recordset.statistic("SMAREA", StatisticMode.SUM);
		recordset.dispose();
		/***************************************************/						
	}
	
	 @Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
			if(keyCode == KeyEvent.KEYCODE_BACK){
				if(!isExitEnable){
					Toast.makeText(this, "�ٰ�һ���˳�����", 1500).show();
					isExitEnable = true;
				}else{
					m_Map.close();
					this.finish();
				}
				return true;
			}
			return super.onKeyDown(keyCode, event);
		}
}