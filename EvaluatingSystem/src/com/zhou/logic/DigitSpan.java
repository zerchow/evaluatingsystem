package com.zhou.logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import android.os.Bundle;
import android.os.Message;

import com.zhou.program.DigitSpanAcousticActivity;
import com.zhou.program.DigitSpanVisualActivity;
import com.zhou.util.FinalUtil;

public class DigitSpan 
{
	private final String[] DIGITSEQUENCES = 
		{
			 "9847253618513629","7465928137615324","1735964829175263","9846351721695873",
			 "5378629149643581","9247381563782156","1289574368691435","6492571386713859",
			 "6835219472893716","3261584791426375","1378659423564819","2839541763426917",
			 "7314965825632418","3291465874823597","9726134854697213","1538976242687193",
			 "3158729649724536","2645391875386917","6754832917426518","3145872691748592",
			 "7645219384659832","9657431824958261","5236479185478293","6279348513781524",
			 "8537269418273941","9674351289281764","8259314679856473","5128379643792648",
			 "4267913857649215","7549163821385479","7324915861286749","6459213873241896",
			 "5398274618562371","7834256915381694","5429617382473815","8279361541927356",
			 "3827149565348679","7431892658962713","9824537161495326","3165287949278361",
			 "2651398471628594","6719835424781365","6498215732416935","7825964132953867",
			 "6819254734361857","2915367486743158","1385967426273598","8542376191253984",
			 "2538967145324871","9257438617315846","6795328415413827","9768325149581637",
			 "7854136298417529","3625941875283961","2178659341639425","9842356175196278",
			 "5413786297953614","7862539419541736","6839174527148362","7348652918714392",
			 "6285937145874316","7426853914532961","9276341585397842","4928367152417835",
			 "9254638174615397","3485762197125648","3516872494968327","7459328163598124",
			 "4913672853689721","1964735826931784","1978542362378569","5162983479672351",
			 "7462981535648327","5471893267649823","6243197856829437","6537482193481725",
			 "8921645735964178","6145827934631759","4529816379574268","8296751348592143",
			 "9867524315486172","8572396418594267","2189437565689743","7139682545329461",
			 "9274516835326489","5721938467826134","5417296832146578","5342961785976438",
			 "6417923857398261","8621374598137945","5931847621623587","8917436527935264",
			 "1862479359721483","8423165979523841","9324586174698537","6431859274295638",
			 "1634785921684537","7596284316182935","6497128351746932","8467351921736458",
			 "4589673121763495","8164359725982413","9374265819578631","1367529481728564",
			 "6978542139825641","7956483124796831","7158439269687254","2178635491786952",
			 "7461259832491786","6189453278367152","5326748919542168","7319524861863427",
			 "1372859647316958","2481635973742568","8195743621786394","5136824796735894",
			 "9184632758596431","9356148728726439","7832961459438576","7594361825241879",
			 "8715249361584732","5379286146419738","7968534214139687","4318759262186573",
			 "7489536219851267","3481965272514936","1672354892375914","6431729858536492",
			 "1489625379372615","5692378416375129","3421697853986241","5986243714293871",
			 "1958723649782436","1937862541374956","5618732948562319","6392875149162738",
			 "9273158461874926","1398652742657398","4976382516458927","1657324984389652",
			 "5284173968165973","8467315298429571","6748921534391687","3156978243514627",
			 "2351768497861423","2397681546572831","1723985647182946","4539178262638794",
			 "4198375624195283","3597612484231756","3716284598379462","7521348967429581",
			 "6879145327134896","8539674212685731","8691524379572316","8921547365684173",
			 "4627158931582364","5624189739437186","5712438692893176","4275961838576193",
			 "8164592375326847","7845962315869471","2639854715684291","5487362917526183",
			 "9813675421382795","5348617296412758","8721539463526984","6937182542458697",
			 "1863724596891542","3924178564276158","3856214971457869","4897165236821435",
			 "5279384161547289","9243561871736529","1967458329716842","8953271647815462",
			 "9153827645214679","4792518636417593","6572934189865247","1647352981853972",
			 "4286571934139578","5239647189467513","1786539429762513","9476832518546297",
			 "4651732982197638","1738496257982134","7523961847235691","4529817361537249",
			 "3854612792675983","3198265748752649","2396481758539261","2853469178342719",
			 "2517638946912758","9453821675197624","4126875939148367","9438261756145739",
			 "4738965124981375","8129563746841972","2956417838752613","7942563813165948"
		};
	//类型
	private int currentType;
	private int currentCount;
	//串长
	private String currentSequence;
	private int currentLength;
	//错误连击
	private int currentErrorHits;
	//正向
	private int forward_TE_ML;
	private int forward_TE_TT;
	private int forward_ML;
	private float forward_MS;
	private ArrayList<String> forward_sequences;
	private ArrayList<String> forward_responses;
	private ArrayList<String> forward_datapools;
	//反向
	private int backward_TE_ML;
	private int backward_TE_TT;
	private int backward_ML;
	private float backward_MS;
	private ArrayList<String> backward_sequences;
	private ArrayList<String> backward_responses;
	private ArrayList<String> backward_datapools;
	//是否为练习
	private boolean isPrac;
	//初始化
	{
		this.forward_TE_ML = -1;
		this.forward_TE_TT = -1;
		this.forward_ML = 0;
		this.forward_MS = 0;
		this.forward_sequences = new ArrayList<String>();
		this.forward_responses = new ArrayList<String>();
		this.forward_datapools = new ArrayList<String>();
		this.backward_TE_ML = -1;
		this.backward_TE_TT = -1;
		this.backward_ML = 0;
		this.backward_MS = 0;
		this.backward_sequences = new ArrayList<String>();
		this.backward_responses = new ArrayList<String>();
		this.backward_datapools = new ArrayList<String>();
		for(int i = 0; i < this.DIGITSEQUENCES.length; i ++)
		{
			this.forward_datapools.add(this.DIGITSEQUENCES[i]);
			this.backward_datapools.add(this.DIGITSEQUENCES[i]);
		}
		//数据先从forward开始
		this.currentType = FinalUtil.DS_FORWARD;
		this.currentLength = 3;
		this.currentErrorHits = 0;
		this.currentCount = 0;
		//先练习
		this.isPrac = true;
	}
	//获取
	private String generateSequence(int length)
	{
		Random random = new Random(System.currentTimeMillis());
		switch(this.currentType)
		{
		case FinalUtil.DS_FORWARD:
			int index_f = random.nextInt(this.forward_datapools.size());
			return this.forward_datapools.remove(index_f).substring(0, length);
		case FinalUtil.DS_BACKWARD:
			int index_b = random.nextInt(this.backward_datapools.size());
			return this.backward_datapools.remove(index_b).substring(0, length);
		default:
			return null;
		}
	}
	//处理用户输入
	public void submitUserInput(String userInput)
	{
		if(this.isPrac)
		{
			this.isPrac = false;
			if(this.currentType == FinalUtil.DS_FORWARD)
			{
				if(this.currentSequence.equals(userInput))
				{
					this.sendEmptyMessage(FinalUtil.DS_CORRECT);
				}
				else
				{
					this.sendEmptyMessage(FinalUtil.DS_ERROR);
				}
			}
			else if(this.currentType == FinalUtil.DS_BACKWARD)
			{
				if(this.currentSequence.equals(
						new StringBuilder(userInput)
						.reverse().toString()))
				{
					this.sendEmptyMessage(FinalUtil.DS_CORRECT);
				}
				else
				{
					this.sendEmptyMessage(FinalUtil.DS_ERROR);
				}
			}
		}
		else
		{
			this.currentCount ++;
			if(this.currentType == FinalUtil.DS_FORWARD)
			{
				this.forward_sequences.add(this.currentSequence);
				this.forward_responses.add(userInput);
				if(this.currentSequence.equals(userInput))
				{
					if(this.forward_ML < this.currentLength)
						this.forward_ML = this.currentLength;
					this.currentErrorHits = 0;
					this.currentLength ++;
				}
				else
				{
					this.currentErrorHits ++;
					if(this.currentErrorHits == 2)
					{
						if(this.forward_TE_ML == -1)
						{
							this.forward_TE_ML = this.currentLength - 1;
							this.forward_TE_TT = this.currentCount - 2;
						}
						this.currentErrorHits = 0;
						if(this.currentLength > 3)
							this.currentLength --;
					}
				}
				this.forwardTestDone();
			}
			else if(this.currentType == FinalUtil.DS_BACKWARD)
			{
				this.backward_sequences.add(this.currentSequence);
				this.backward_responses.add(userInput);
				if(this.currentSequence.equals(
						new StringBuilder(userInput)
						.reverse().toString()))
				{
					if(this.backward_ML < this.currentLength)
						this.backward_ML = this.currentLength;
					this.currentErrorHits = 0;
					this.currentLength ++;
				}
				else
				{
					this.currentErrorHits ++;
					if(this.currentErrorHits == 2)
					{
						if(this.backward_TE_ML == -1)
						{
							this.backward_TE_ML = this.currentLength - 1;
							this.backward_TE_TT = this.currentCount - 2;
						}
						this.currentErrorHits = 0;
						if(this.currentLength > 2)
							this.currentLength --;
					}
				}
				this.backwardTestDone();
			}
		}
	}
	//
	private void forwardTestDone()
	{
		if(this.currentCount == 14)
		{
			this.currentCount = 0;
			this.currentErrorHits = 0;
			this.currentLength = 2;
			this.currentType = FinalUtil.DS_BACKWARD;
			this.isPrac = true;
			Bundle bundle = new Bundle();
			bundle.putInt("fteml", this.forward_TE_ML);
			bundle.putInt("fml", this.forward_ML);
			this.sendMessage(FinalUtil.DS_FORWARDEND,bundle);
		}
		else
		{
			this.sendEmptyMessage(FinalUtil.DS_START);
		}
	}
	//
	private void backwardTestDone()
	{
		if(this.currentCount == 14)
		{
			Bundle bundle = new Bundle();
			bundle.putInt("bteml", this.backward_TE_ML);
			bundle.putInt("bml", this.backward_ML);
			this.sendMessage(FinalUtil.DS_BACKWARDEND,bundle);
		}
		else
		{
			this.sendEmptyMessage(FinalUtil.DS_START);
		}
	}
	//获取数据的接口
	public void getSequence()
	{
		Bundle bundle = new Bundle();
		if(this.isPrac)
		{
			Random random = new Random(System.currentTimeMillis());
			this.currentSequence = this.DIGITSEQUENCES
					[random.nextInt(this.DIGITSEQUENCES.length)]
							.substring(0, 3);
			bundle.putInt("type", this.currentType);
			bundle.putString("sequence", this.currentSequence);
			if(this.currentType == FinalUtil.DS_FORWARD)
			{
				this.sendMessage(FinalUtil.DS_FORWARDPRAC,
						bundle);
			}
			else if(this.currentType == FinalUtil.DS_BACKWARD)
			{
				this.sendMessage(FinalUtil.DS_BACKWARDPRAC,
						bundle);
			}
		}
		else
		{
			this.currentSequence = this.generateSequence(
					this.currentLength);
			bundle.putInt("type", this.currentType);
			bundle.putString("sequence", this.currentSequence);
			if(this.currentType == FinalUtil.DS_FORWARD)
			{
				this.sendMessage(FinalUtil.DS_FORWARDTEST,
						bundle);
			}
			else if(this.currentType == FinalUtil.DS_BACKWARD)
			{
				this.sendMessage(FinalUtil.DS_BACKWARDTEST,
						bundle);
			}
		}
	}
	//
	public float[] getResult()
	{
		HashMap<Integer,Integer> forwardmap_a = new HashMap<Integer, Integer>();
		HashMap<Integer,Integer> forwardmap_c = new HashMap<Integer, Integer>();
		HashMap<Integer,Integer> backwardmap_a = new HashMap<Integer, Integer>();
		HashMap<Integer,Integer> backwardmap_c = new HashMap<Integer, Integer>();
		for(int i = 0; i < this.forward_sequences.size(); i ++)
		{
			String str = this.forward_sequences.get(i);
			int size = str.length();
			if(forwardmap_a.get(size) == null)
			{
				forwardmap_a.put(size, 0);
				forwardmap_c.put(size, 0);
			}
			if(this.forward_responses.get(i).equals(str))
			{
				forwardmap_c.put(size, forwardmap_c.get(size) + 1);
			}
			forwardmap_a.put(size, forwardmap_a.get(size) + 1);
		}
		for(int i = 0; i < this.backward_sequences.size(); i ++)
		{
			String str = this.backward_sequences.get(i);
			int size = str.length();
			if(backwardmap_a.get(size) == null)
			{
				backwardmap_a.put(size, 0);
				backwardmap_c.put(size, 0);
			}
			if(this.backward_responses.get(i).equals(str))
			{
				backwardmap_c.put(size, backwardmap_c.get(size) + 1);
			}
			backwardmap_a.put(size, backwardmap_a.get(size) + 1);
		}
		this.forward_MS += 2.5;
		for(Entry<Integer, Integer> entry : forwardmap_a.entrySet())
		{
			if(entry.getValue().intValue() == 0)
			{
				continue;
			}
			this.forward_MS += forwardmap_c.get(entry.getKey()) / (float)entry.getValue();
		}
		this.backward_MS += 1.5;
		for(Entry<Integer, Integer> entry : backwardmap_a.entrySet())
		{
			if(entry.getValue().intValue() == 0)
			{
				continue;
			}
			this.backward_MS += backwardmap_c.get(entry.getKey()) / (float)entry.getValue();
		}
		float[] result = new float[8];
		result[0] = this.forward_TE_ML;
		result[1] = this.forward_TE_TT;
		result[2] = this.forward_ML;
		result[3] = this.forward_MS;
		//
		result[4] = this.backward_TE_ML;
		result[5] = this.backward_TE_TT;
		result[6] = this.backward_ML;
		result[7] = this.backward_MS;
		return result;
	}
	//
	private DigitSpanVisualActivity contextVisual = null;
	private DigitSpanAcousticActivity contextAcoustic = null;
	public DigitSpan(DigitSpanVisualActivity context)
	{
		this.contextVisual = context;
	}
	public DigitSpan(DigitSpanAcousticActivity context)
	{
		this.contextAcoustic = context;
	}
	private void sendEmptyMessage(int what)
	{
		if(this.contextVisual == null)
		{
			this.contextAcoustic.handler.sendEmptyMessage(what);
		}
		else if(this.contextAcoustic == null)
		{
			this.contextVisual.handler.sendEmptyMessage(what);
		}
	}
	private void sendMessage(int what, Bundle bundle)
	{
		Message msg = new Message();
		msg.what = what;
		msg.setData(bundle);
		if(this.contextVisual == null)
		{
			this.contextAcoustic.handler.sendMessage(msg);
		}
		else if(this.contextAcoustic == null)
		{
			this.contextVisual.handler.sendMessage(msg);
		}
	}
}