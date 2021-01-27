package com.jadenyee.controller;

import com.jadenyee.constant.MessageConstant;
import com.jadenyee.entity.Result;
import com.jadenyee.service.MemberService;
import com.jadenyee.service.OrderService;
import com.jadenyee.service.SetmealService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    private MemberService memberService;
    @Reference
    private SetmealService setmealService;
    @Reference
    private OrderService orderService;

    /**
     * 获取会员的年度信息数据统计
     *
     * @return 封装的数据集合
     */
    @GetMapping("/getMemberReport")
    public Result getMemberStatistics() {
        try {
            Map<String, List> res = memberService.getMemberStatistics4PastYear();
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, res);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

    /**
     * 获取套餐的预约统计数据
     *
     * @return 返回数据封装
     */
    @GetMapping("/getSetmealReport")
    public Result getSetmealReport() {
        try {
            Map<String, List> res = setmealService.getSetmealStatistics();
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, res);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }

    /**
     * 整合预约，套餐，会员数据
     *
     * @return 返回数据封装
     */
    @GetMapping("/getBusinessReportData")
    public Result getDailyReport() {
        try {
            LocalDate date = LocalDate.now();
            Map<String, Object> dailyStatistic = new HashMap<>();
            List<Map<String, Object>> setmealStat = setmealService.getHotSetmealStat();
            Map<String, Integer> orderStat = orderService.getOrderStat();
            Map<String, Integer> memberStatisDaily = memberService.getMemberStatisDaily();
            dailyStatistic.put("hotSetmeal", setmealStat);
            dailyStatistic.putAll(orderStat);
            dailyStatistic.putAll(memberStatisDaily);
            dailyStatistic.put("reportDate", date.toString());
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, dailyStatistic);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    /**
     * 将经营数据编写为表格导出
     *
     * @param request HttpRequest
     * @return 返回消息数据封装结果
     */
    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response) {
        try {

            String reportDate = LocalDate.now().toString();
            List<Map<String, Object>> setmealStat = setmealService.getHotSetmealStat();
            Map<String, Integer> orderStat = orderService.getOrderStat();
            Map<String, Integer> memberStatisDaily = memberService.getMemberStatisDaily();
            Integer todayNewMember = memberStatisDaily.get("todayNewMember");
            Integer totalMember = memberStatisDaily.get("totalMember");
            Integer thisWeekNewMember = memberStatisDaily.get("thisWeekNewMember");
            Integer thisMonthNewMember = memberStatisDaily.get("thisMonthNewMember");
            Integer todayOrderNumber = orderStat.get("todayOrderNumber");
            Integer thisWeekOrderNumber = orderStat.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = orderStat.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = orderStat.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = orderStat.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = orderStat.get("thisMonthVisitsNumber");

            //获取模板文件的真实路径 File.separator 会根据不同的操作系统设置文件分隔符
            String realPath = request.getSession().getServletContext().getRealPath("template" + File.separator + "report_template.xlsx");
            XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(realPath)));
            XSSFSheet sheet = workbook.getSheetAt(0);
            XSSFRow row = sheet.getRow(2);
            row.getCell(5).setCellValue(reportDate);
            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);
            row.getCell(7).setCellValue(totalMember);
            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);
            row.getCell(7).setCellValue(thisMonthNewMember);
            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);
            row.getCell(7).setCellValue(todayVisitsNumber);
            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);
            row.getCell(7).setCellValue(thisWeekVisitsNumber);
            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);
            row.getCell(7).setCellValue(thisMonthVisitsNumber);
            int rowNum = 12;
            XSSFCellStyle rowStyle = sheet.getRow(12).getRowStyle();
            XSSFCellStyle cellStyle = row.getCell(4).getCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setWrapText(true);
            for (Map<String, Object> statMap : setmealStat) {
                String name = (String) statMap.get("name");
                Long setmeal_count = (Long) statMap.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) statMap.get("proportion");
                if (rowNum <= 15) {
                    row = sheet.getRow(rowNum++);
                    row.getCell(4).setCellValue(name);
                    row.getCell(5).setCellValue(setmeal_count);
                    row.getCell(6).setCellValue(proportion.doubleValue());
                }else{
                    row = sheet.createRow(rowNum);
                    row.setRowStyle(rowStyle);
                    XSSFCell c1 = row.createCell(4);
                    XSSFCell c2 = row.createCell(5);
                    XSSFCell c3 = row.createCell(6);
                    XSSFCell c4 = row.createCell(7);
                    c4.setCellValue("");
                    c4.setCellStyle(cellStyle);
                    c1.setCellStyle(cellStyle);
                    c1.setCellValue(name);
                    c2.setCellStyle(cellStyle);
                    c2.setCellValue(setmeal_count);
                    c3.setCellStyle(cellStyle);
                    c3.setCellValue(proportion.doubleValue());
                }
            }
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=DailyDataReport.xlsx");
            workbook.write(out);
            out.flush();
            out.close();
            workbook.close();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL, null);
        }
    }
}
