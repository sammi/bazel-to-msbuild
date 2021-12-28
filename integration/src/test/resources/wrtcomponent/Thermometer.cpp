#include "pch.h"
#include "Thermometer.h"
#include "Thermometer.g.cpp"

namespace winrt::ThermometerWRC::implementation
{
    void Thermometer::AdjustTemperature(float deltaFahrenheit)
    {
        m_temperatureFahrenheit += deltaFahrenheit;
    }
}
