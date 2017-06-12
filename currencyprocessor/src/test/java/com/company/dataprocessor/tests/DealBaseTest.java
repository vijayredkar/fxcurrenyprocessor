package com.company.dataprocessor.tests;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DealBaseTest.class)
public abstract class DealBaseTest 
{
  Logger logger = LoggerFactory.getLogger(DealBaseTest.class);
}
