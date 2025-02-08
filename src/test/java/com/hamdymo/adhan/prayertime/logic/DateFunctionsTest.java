package com.hamdymo.adhan.prayertime.logic;

import com.hamdymo.adhan.prayertime.facade.FileFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class DateFunctionsTest {
    @Mock
    private FileFacade fileFacade;

    @InjectMocks
    private DateFunctions dateFunctions;



    @Test
    void getDateTomorrow() {
    }

    @Test
    void addMinutesToHourMinuteString() {
        String test = "14:30";
        int howMuchToAdd = 25;

        String result = dateFunctions.addMinutesToHourMinuteString(test, howMuchToAdd);
        assertEquals("14:55", result);
    }

    @Test
    void setTimeOfDayToString() {
    }
}