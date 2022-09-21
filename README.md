# parameterTest
 Simulations to test the fit of a equation to real life stock prices fitted in a sql table. Testing to see how the time complexity of various looping and testing methods worked out.
 
 Tries to fit equation of asin((2pi/p(x-h)) + mx + b to short term and long term data
 where 
 a = amplitude
 p = period
 h = phase shift
 m = slope
 b = vertical displacement
 
 uses (z(a1sin((2pi/p1(x-h1)) + m1x + b1) + k(a2sin((2pi/p2(x-h2)) + m2x + b2))/2
where k + z = 1 
to calculate percentage change of increase in price

