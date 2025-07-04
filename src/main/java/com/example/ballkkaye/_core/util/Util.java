package com.example.ballkkaye._core.util;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

public class Util {

    /**
     * 팀의 전체 이름에서 약칭(앞 단어)만 추출
     * 예: "두산 베어스" → "두산"
     */
    public static String extractTeamShortName(String fullName) {
        if (fullName == null || fullName.isBlank()) return "";
        return fullName.split(" ")[0]; // 공백 앞의 첫 단어 반환
    }

    /**
     * 맞대결 전적 페이지 셀렉트 박스의 옵션 중 keyword를 포함한 항목을 선택
     * 포함된 항목이 없으면 예외 반환
     */
    public static void selectByContainingText(Select select, String keyword) {
        boolean found = false;
        for (WebElement option : select.getOptions()) {
            if (option.getText().contains(keyword)) {
                select.selectByVisibleText(option.getText());
                found = true;
                break;
            }
        }
        if (!found) throw new NoSuchElementException("❌ 드롭다운에서 '" + keyword + "' 포함된 옵션 없음");
    }


    /**
     * 타자의 시즌 타율 문자열을 Double로 안전하게 파싱.
     * 마지막 컬럼에서 값을 가져오며, "-" 또는 공백이면 0.0을 반환
     */
    public static Double parseSeasonAVG(List<WebElement> cols) {
        try {
            String avgText = cols.get(cols.size() - 1).getText().trim(); // 예: ".294", "-" 등
            if (avgText.equals("-") || avgText.isEmpty()) return null; // 정보 없음은 null
            return Double.parseDouble(avgText);
        } catch (Exception e) {
            return null; // 파싱 실패도 null 처리
        }
    }


    /**
     * 문자열을 Integer로 변환하되, 실패 시 0을 반환
     */
    public static int parseIntSafe(String s) {
        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 문자열을 double로 변환하되, 실패 시 0.0을 반환
     */
    public static double parseDoubleSafe(String s) {
        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return 0.0;
        }
    }
}
