package com.codesdream.ase.service;
import com.codesdream.ase.exception.innerservererror.InvalidDataException;
import com.codesdream.ase.model.chart.BarChart;
import com.codesdream.ase.model.chart.LineChart;
import com.codesdream.ase.model.chart.FanChart;
import com.codesdream.ase.model.chart.Chart;
import com.codesdream.ase.model.chart.Form;
import org.springframework.stereotype.Service;
import java.lang.*;

@Service

public class VisualService {


    /**
     * 用于创建条形统计图的数据表
     * @author gzq
     * @param data 一个表格，第一行为x，第二行为y,(只有两行，每行列数相同，类型为string)
     * @param xname x坐标名称
     * @param yname y坐标名称
     * @return 创建的数据表对象
     */
    public BarChart createBarChart(Form data,String xname,String yname){
        BarChart r=new BarChart();
        r.title= data.title;
        r.xname=xname;
        r.yname=yname;

        if(data.body.length!=2){
           throw new InvalidDataException( "表格行数只能为2");
        }
        int len=data.body[0].length;
        try{
            for(int i=0;i<len;i++){

            r.x[i]=data.body[0][i];
            r.y[i]=Double.parseDouble(data.body[1][i]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
       return r;
    }

    /**
     * 用于创建折线统计图的数据表
     * @author gzq
     * @param data 一个表格，第一行为x，第二行为y,(只有两行，每行列数相同，类型为string)
     * @param xname x坐标名称
     * @param yname y坐标名称
     * @return 创建的数据表对象
     */

    public LineChart createLineChart(Form data,String xname,String yname){
        LineChart r=new LineChart();
        r.title=data.title;
        r.xname=xname;
        r.yname=yname;

        if(data.body.length!=2){
            throw new InvalidDataException( "表格行数只能为2");
        }
        int len=data.body[0].length;
        try{
            for(int i=0;i<len;i++){

                r.x[i]=Double.parseDouble(data.body[0][i]);
                r.y[i]=Double.parseDouble(data.body[1][i]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return r;
    }

    /**
     * 用于创建扇形统计图的数据表
     * @author gzq
     * @param data 一个表格，第一行为x，第二行为y,(只有两行，每行列数相同，类型为string，y之和为100)
     * @return 创建的数据表对象
     */
    public FanChart createFanChart(Form data){
        FanChart r=new FanChart();
        r.title= data.title;
        if(data.body.length!=2){
            throw new InvalidDataException( "表格行数只能为2");
        }
        int len=data.body[0].length;
        try{
            for(int i=0;i<len;i++){

                r.x[i]=data.body[0][i];
                r.y[i]=Double.parseDouble(data.body[1][i]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return r;
    }





}
