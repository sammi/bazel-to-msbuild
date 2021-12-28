#pragma once

#include "Thermometer.g.h"

namespace winrt::ThermometerWRC::implementation
{
    struct Thermometer : ThermometerT<Thermometer>
    {
        Thermometer() = default;
        void AdjustTemperature(float deltaFahrenheit);
    private:
        float m_temperatureFahrenheit{ 0.f };
    };
}

namespace winrt::ThermometerWRC::factory_implementation
{
    struct Thermometer : ThermometerT<Thermometer, implementation::Thermometer>
    {
    };
}
