package mypackage;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javax.xml.rpc.ServiceException;

public class WeatherApp {
    public static void main(String[] args) {

        WeatherWebService weatherService = new WeatherWebServiceLocator();
        WeatherWebServiceSoap_PortType weatherSoap = null;
        try {
            weatherSoap = weatherService.getWeatherWebServiceSoap();
        } catch (ServiceException e) {
            System.out.println("获取天气服务时发生错误：" + e.getMessage());
            return;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入城市名：");
        String city = scanner.nextLine();

        if (city == null || city.trim().isEmpty()) {
            System.out.println("城市名不能为空，请重新输入！");
            scanner.close();
            return;
        }

        System.out.println("查询中，请稍候...");
        try {
            String[] weatherInfoArray = weatherSoap.getWeatherbyCityName(city);
            List<String> weatherInfo = Arrays.asList(weatherInfoArray);

            System.out.println("天气信息：");
            for (String info : weatherInfo) {
                // 过滤掉包含 ".gif" 和 ".jpg" 的信息
                if (!info.toLowerCase().contains(".gif") && !info.toLowerCase().contains(".jpg")) {
                    System.out.println(info);
                }
            }
        } catch (Exception e) {
            System.out.println("获取天气信息时发生错误：" + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
