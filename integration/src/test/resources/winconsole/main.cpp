#include "pch.h"

using namespace winrt;
using namespace Windows::Foundation;

int main()
{
    Uri uri(L"http://aka.ms/cppwinrt");
    printf("Hello, %ls!\n", uri.AbsoluteUri().c_str());
}
