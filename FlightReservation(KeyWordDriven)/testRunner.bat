Set ProjectLocation=C:\Selenium\workspace\FlightReservation(KeyWordDriven)
cd %ProjectLocation%
Set classPath=%ProjectLocation%\bin;%ProjectLocation%\libs\*
java org.testng.TestNG testng.xml
pause