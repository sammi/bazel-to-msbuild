#include <gtest/gtest.h>

int factorial(int n){
  int ret = 1;
  for(int i = 2; i <= n; ++i){
    ret *= i;
  }
  return ret;
}

TEST(FactorialTest, HandlesZeroInput) {
  EXPECT_EQ(factorial(0), 1);
}

TEST(FactorialTest, HandlesPositiveInput) {
  EXPECT_EQ(factorial(1), 1);
  EXPECT_EQ(factorial(2), 2);
  EXPECT_EQ(factorial(3), 6);
  EXPECT_EQ(factorial(8), 40320);
}
