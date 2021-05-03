package TestClasses;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({

    TestGetRestAPI.class,
    TestPOSTRestAPI.class,
    TestPUTRestAPI.class,
    TestDeleteRestAPI.class,

})

public class SuiteClass {}
