package com.smlnskgmail.jaman.hashchecker.calculator.jdk.crc;

import androidx.annotation.NonNull;

import com.smlnskgmail.jaman.hashchecker.calculator.jdk.BaseJDKHashCalculatorTest;
import com.smlnskgmail.jaman.hashchecker.logic.hashcalculator.HashType;

public class CRC32JDKHashCalculatorTest extends BaseJDKHashCalculatorTest {

    @NonNull
    @Override
    protected HashType getHashType() {
        return HashType.CRC_32;
    }

    @NonNull
    @Override
    protected String getHashValueForTestText() {
        return "eeea93b8";
    }

    @NonNull
    @Override
    protected String getHashValueForTestFile() {
        return "60c7400a";
    }

}