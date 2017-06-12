package com.tachyon5.spectrum.common;

import org.json.JSONException;
import org.json.JSONObject;

import com.tachyon5.spectrum.utils.Contents;

public class JsonManger {
	
	public static String dark="254	41687	41658	41650	41651	41655	41658	41669	41678	41688	41697	41704	41700	41710	41718	41723	41723	41727	41733	41733	41733	41733	41734	41732	41738	41736	41735	41738	41736	41737	41736	41738	41737	41738	41738	41734	41734	41735	41738	41735	41740	41742	41744	41745	41745	41745	41744	41737	41737	41731	41736	41734	41736	41734	41737	41733	41731	41735	41737	41736	41733	41729	41730	41733	41731	41736	41733	41733	41736	41741	41741	41740	41742	41744	41743	41742	41740	41743	41739	41733	41736	41741	41742	41742	41737	41744	41742	41745	41748	41751	41746	41742	41743	41744	41743	41744	41747	41743	41740	41738	41739	41742	41740	41742	41743	41738	41738	41731	41734	41741	41743	41747	41745	41745	41746	41743	41733	41739	41741	41745	41747	41740	41745	41743	41739	41740	41742	41748	41749	41752	41755	41755	41753	41752	41747	41749	41749	41750	41752	41747	41746	41739	41741	41745	41751	41753	41747	41744	41744	41737	41738	41746	41745	41744	41753	41754	41757	41755	41759	41751	41746	41750	41747	41747	41750	41750	41748	41746	41744	41749	41746 41747	41739	41742	41742	41742	41746	41749	41748	41748	41746	41743	41752	41752	41745	41752	41758	41748	41747	41747	41749	41744	41741	41740	41743	41746	41744	41739	41742	41739	41735	41736	41735	41739	41732	41735	41734	41733	41729	41734	41736	41736	41740	41739	41731	41735	41742	41744	41748	41738	41744	41739	41747	41748	41745	41749	41741	41737	41737	41737	41740	41737	41737	41743	41743	41745	41748	41746	41742	41745	41741	41744	41746	41747	41739	41739	41740	41739	41744	41743	41742	41742	41739	41741	41746	41742	41741	41743	41736	41736	41736	41743	41744	41741	41744	41733	41737	41741	41740	41738	41737	41740	41736	41739	41740	41736	41734	41734	41732	41735	41738	41742	41742	41740	41740	41740	41740	41743	41741	41739	41741	41741	41741	41741	41740	41738	41736	41743	41736	41734	41734	41729	37729";
	public static String ref="228	55147	55137	55120	55097	55073	55047	55019	54990	54961	54933	54905	54875	54848	54819	54790	54762	54733	54703	54674	54643	54614	54584	54552	54521	54491	54460	54433	54402	54369	54339	54307	54279	54245	54213	54179	54149	54116	54086	54051	54018	53988	53952	53921	53889	53856	53826	53793	53762	53727	53695	53663	53631	53598	53569	53536	53507	53476	53445	53414	53386	53356	53325	53297	53268	53240	53211	53185	53157	53130	53100	53077	53048	53022	52995	52969	52940	52914	52890	52863	52837	52810	52783	52756	52732	52707	52680	52653	52630	52604	52582	52558	52534	52509	52488	52464	52443	52421	52401	52378	52361	52340	52318	52302	52285	52265	52247	52230	52211	52194	52178	52161	52142	52125	52107	52092	52072	52056	52039	52023	52006	51986	51968	51954	51936	51916	51899	51882	51864	51848	51826	51813	51793	51780	51761	51747	51731	51719	51705	51695	51682	51670	51659	51653	51644	51636	51631	51625	51621	51619	51617	51615	51612	51613	51610	51613	51613	51616	51615	51617	51617	51619	51619	51623	51622	51626	51625	51625	51625	51621	51622	51617	51614	51609	51605	51598	51593	51585	51577	51571	51562	51552	51542	51531	51522	51510	51496	51484	51472	51458	51445	51432	51420	51405	51393	51381	51367	51358	51346	51334	51322	51310	51301	51289	51280	51270	51261	51252	51245	51234	51229	51222	51214	51212	51206	51199	51194	51191	51186	51182	51180	51175	51172	51167	51165	51160	51158	51156	51150	51149	51147	51142	51139	51134	51131	51126	51124	51118	51115	51110	51105	51100	51095	51091	51087	51080	51073	51072	51066	51061	51057	51055	51047	51047	51040	51040	51036	51034	51032	51028	51027	51022	51023	51024	51020	51019	51019	51015	51014	51010	51005	51001	50995	50988	50981	50973	50964	50953	50940	50924	50909	50890	50870	50845	50820	50791	50758	50726	50691	50647	50615	50577	50531	50482	50426	50362	50297	50236	50171	50095	50010	49914	37690";
	public static String sample="216 55067 55065 55062 55054 55041 55032	55017	55004	54989	54974	54958	54942	54926	54910	54895	54879	54858	54842	54827	54808	54790	54774	54757	54741	54722	54704	54685	54669	54654	54637	54620	54601	54589	54568	54551	54534	54517	54499	54484	54464	54449	54429	54414	54398	54380	54363	54345	54327	54310	54291	54275	54259	54244	54227	54210	54195	54178	54161	54147	54132	54116	54100	54084	54072	54058	54046	54031	54017	54004	53989	53976	53962	53946	53933	53920	53904	53890	53874	53859	53845	53830	53816	53802	53786	53773	53757	53744	53730	53716	53704	53693	53682	53671	53659	53650	53640	53633	53624	53616	53611	53604	53598	53591	53586	53582	53577	53574	53571	53564	53563	53558	53554	53552	53548	53545	53541	53541	53537	53535	53533	53530	53527	53526	53523	53521	53520	53518	53517	53518	53516	53517	53519	53520	53521	53523	53525	53528	53531	53533	53536	53538	53546	53551	53555	53562	53568	53575	53582	53592	53603	53612	53625	53639	53654	53670	53687	53705	53730	53749	53770	53795	53821	53845	53875	53900	53933	53962	53993	54024	54061	 54095	54131	54169	54204	54245	54285	54325	54373	54412	54454	54497	54543	54584	54627	54668	54709	54745	54785	54827	54863	54898	54933	54965	54998	55027	55054	55083	55108	55135	55161	55187	55211	55237	55260	55287	55312	55340	55366	55394	55422	55454	55484	55511	55540	55571	55602	55636	55662	55690	55722	55753	55782	55808	55840	55866	55893	55922	55946	55970	55994	56017	56043	56065	56086	56105	56126	56143	56161	56179	56199	56214	56229	56246	56259	56271	56282	56295	56303	56317	56322	56334	56339	56348	56354	56360	56367	56375	56381	56388	56392	56397	56401	56406	56410	56413	56413	56419	56418	56417	56417	56416	56413	56409	56405	56397	56390	56379	56367	56357	56342	56324	56306	56284	56260	56235	56206	56173	56138	56100	56069	56035	55992	55944	55893	55834	55778	55729	55671	55602	55527	55444	39393";
	//创建会话
	public static String getJsonStr_1(String mac) throws JSONException{
		
		if(mac==null||mac.length()<1){
			mac="123456789";
		}
		
		JSONObject json = new JSONObject();
		json.put(Contents.JSON_KEY_SESSIONID, "");
		json.put(Contents.JSON_KEY_OPENID, "mi");
		json.put(Contents.JSON_KEY_ACTION, Contents.ACTION_CREATE_SESSION);
		json.put(Contents.JSON_KEY_TYPE, Contents.MSG_REQ);
		JSONObject data = new JSONObject();
		data.put("apptype", Contents.APPTYPE_ACTION);
		data.put("approle", "1"); // 0 ios 1 android
		data.put("logintype", "1"); // 0 微信登录 1 游客登录
		data.put("protocol", "2.0"); // 版本号
		data.put("username", "usermagispec");
		data.put("password", "11qqaazZ");
		data.put("mac", mac);
		
		json.put(Contents.JSON_KEY_DATA, data);
		
		return json.toString();
	}
	
	//app版本号查询
	public static String getJsonStr_2() throws JSONException{
		JSONObject json = new JSONObject();
		json.put(Contents.JSON_KEY_SESSIONID, null);
		json.put(Contents.JSON_KEY_OPENID, "MAGISPEC_NO_SESSION_ID_FF");
		json.put(Contents.JSON_KEY_ACTION, Contents.ACTION_START_GET_APP);
		json.put(Contents.JSON_KEY_TYPE, Contents.MSG_REQ);
		json.put(Contents.JSON_KEY_DATA, "");
		return json.toString();
	}
	
	//本地算法数据
	public static String getJsonStr_3() throws JSONException{
		JSONObject json = new JSONObject();
		json.put(Contents.JSON_KEY_SESSIONID, Contents.seesionID);
		json.put(Contents.JSON_KEY_OPENID, "mi");
		json.put(Contents.JSON_KEY_ACTION, Contents.ACTION_START_GET_LOCALPARAM);
		json.put(Contents.JSON_KEY_TYPE, Contents.MSG_REQ);
		json.put(Contents.JSON_KEY_DATA, "");
		return json.toString();
		}
		
		//dark ref 上传
		public static String getJsonStr_4(String strDark,String strRef) throws JSONException{
			JSONObject json = new JSONObject();
			json.put(Contents.JSON_KEY_SESSIONID,  Contents.seesionID);
			json.put(Contents.JSON_KEY_OPENID, "mi");
			json.put(Contents.JSON_KEY_ACTION, Contents.ACTION_SHIELD_POLICE_UPLOAD_DARKREF_DATE);
			json.put(Contents.JSON_KEY_TYPE, Contents.MSG_REQ);
			JSONObject data = new JSONObject();
			//data.put("dark", dark.replace("\t", " "));
			//data.put("ref", ref.replace("\t", " "));
			data.put("dark", strDark);
			data.put("ref", strRef);
			json.put(Contents.JSON_KEY_DATA, data);
			return json.toString();
		}	
		
		//dark ref 上传调试
		public static String getJsonStr_4_1(String strDark,String strRef) throws JSONException{
			JSONObject json = new JSONObject();
			json.put(Contents.JSON_KEY_SESSIONID,  Contents.seesionID);
			json.put(Contents.JSON_KEY_OPENID, "mi");
			json.put(Contents.JSON_KEY_ACTION, Contents.ACTION_SHIELD_POLICE_UPLOAD_DARKREF_DATE);
			json.put(Contents.JSON_KEY_TYPE, Contents.MSG_REQ);
			JSONObject data = new JSONObject();
			data.put("dark", dark.replace("\t", " "));
			data.put("ref", ref.replace("\t", " "));
			json.put(Contents.JSON_KEY_DATA, data);
			return json.toString();
		}
		
		// 检测
		public static String getJsonStr_5(String darkrefID,String sample,int[] ret,double[][] rgb) throws JSONException{
			JSONObject json = new JSONObject();
			json.put(Contents.JSON_KEY_SESSIONID, Contents.seesionID);
			json.put(Contents.JSON_KEY_OPENID, "mi");
			json.put(Contents.JSON_KEY_ACTION, Contents.ACTION_START_GET_DETECTION);
			json.put(Contents.JSON_KEY_TYPE, Contents.MSG_REQ);
			JSONObject data = new JSONObject();
			data.put("retl1", ret[0]+"");
			data.put("retl2", ret[1]+"");
			data.put("retl3", ret[2]+"");
			data.put("rgb1", getStrOfDouble(rgb[0]));
			data.put("rgb2", getStrOfDouble(rgb[1]));
			data.put("rgb3", getStrOfDouble(rgb[2]));
			data.put("rgb4", getStrOfDouble(rgb[3]));
			data.put("rgb5", getStrOfDouble(rgb[4]));
			data.put("rgb6", getStrOfDouble(rgb[5]));
			data.put("spec",sample );//sample.replace("\t", " ")
			data.put("darkrefid", darkrefID);
			data.put("position", "地址定位");
			json.put(Contents.JSON_KEY_DATA, data);
			return json.toString();
		}	
		
		// 检测调试
				public static String getJsonStr_5_1(String darkrefID,String samples,int[] ret,double[][] rgb) throws JSONException{
					JSONObject json = new JSONObject();
					json.put(Contents.JSON_KEY_SESSIONID, Contents.seesionID);
					json.put(Contents.JSON_KEY_OPENID, "mi");
					json.put(Contents.JSON_KEY_ACTION, Contents.ACTION_START_GET_DETECTION);
					json.put(Contents.JSON_KEY_TYPE, Contents.MSG_REQ);
					JSONObject data = new JSONObject();
					data.put("retl1", "1");
					data.put("retl2", "0");
					data.put("retl3", "0");
					data.put("rgb1", "123,123,123");
					data.put("rgb2", "123,123,123");
					data.put("rgb3", "123,123,123");
					data.put("rgb4", "123,123,123");
					data.put("rgb5", "123,123,123");
					data.put("rgb6", "123,123,123");
					data.put("spec",sample.replace("\t", " "));
					data.put("darkrefid", "12");
					data.put("position", "地址定位");
					json.put(Contents.JSON_KEY_DATA, data);
					return json.toString();
				}
		
		//获取记录
		public static String getJsonStr_6() throws JSONException{
			JSONObject json = new JSONObject();
			json.put(Contents.JSON_KEY_SESSIONID,  Contents.seesionID);
			json.put(Contents.JSON_KEY_OPENID, "mi");
			json.put(Contents.JSON_KEY_ACTION, Contents.ACTION_START_GET_RECORDS);
			json.put(Contents.JSON_KEY_TYPE, Contents.MSG_REQ);
			JSONObject data = new JSONObject();
			data.put("start", "0");
			data.put("count", "10");
			json.put(Contents.JSON_KEY_DATA, data);
			return json.toString();
		}
		
		//获取通知
		public static String getJsonStr_7() throws JSONException{
			JSONObject json = new JSONObject();
			json.put(Contents.JSON_KEY_SESSIONID,  Contents.seesionID);
			json.put(Contents.JSON_KEY_OPENID, "mi");
			json.put(Contents.JSON_KEY_ACTION, Contents.ACTION_START_GET_NOTIFY);
			json.put(Contents.JSON_KEY_TYPE, Contents.MSG_REQ);
			json.put(Contents.JSON_KEY_DATA, "");
			return json.toString();
		}
		
		//删除记录
		public static String getJsonStr_8(String redID) throws JSONException{
			JSONObject json = new JSONObject();
			json.put(Contents.JSON_KEY_SESSIONID,  Contents.seesionID);
			json.put(Contents.JSON_KEY_OPENID, "mi");
			json.put(Contents.JSON_KEY_ACTION, Contents.ACTION_START_DEL_RECORD);
			json.put(Contents.JSON_KEY_TYPE, Contents.MSG_REQ);
			JSONObject data = new JSONObject();
			data.put("recid", redID);
			json.put(Contents.JSON_KEY_DATA, data);
			return json.toString();
		}
				
		
		
		 
		// doubele 数组转换为string
		private static String getStrOfDouble(double[] dou){
			 String str="";
			 for(int i=0;i<dou.length;i++){
				 str= (i==dou.length) ? str+dou[i] : str+dou[i]+" ";
			 }
			  
			 return str;
		  }
}
