from tkinter import Tk, Scale, Label, Button, CENTER, HORIZONTAL, DoubleVar, StringVar

import constant as cnst
import paho.mqtt.client as mqtt

class Simulator:

    def __init__(self):
        # MQTT Client
        self.client = mqtt.Client(client_id="simulator")
        self.client.connect("localhost", port=1883)

        # Window settings
        self.window = Tk()
        self.window.title(cnst.WINDOW_TITLE)
        self.window.geometry(str(cnst.WINDOW_WIDTH) +
                             'x'+str(cnst.WINDOW_HEIGHT))

        # Temperature slider
        self.temp_lbl = Label(self.window, text="Temperature")
        self.temp_lbl.pack(anchor=CENTER)
        self.temp_var = DoubleVar(value=cnst.DEFAULT_TEMP)
        self.slider_temperature = Scale(self.window, variable=self.temp_var,
                                        from_=cnst.MIN_TEMP, to_=cnst.MAX_TEMP, resolution=-1, orient=HORIZONTAL)
        self.slider_temperature.pack(anchor=CENTER)

        # API Temperature slider
        self.api_temp_lbl = Label(self.window, text="API Temperature")
        self.api_temp_lbl.pack(anchor=CENTER)
        self.api_temp_var = DoubleVar(value=cnst.DEFAULT_TEMP)
        self.slider_api_temperature = Scale(self.window, variable=self.api_temp_var,
                                            from_=cnst.MIN_TEMP, to_=cnst.MAX_TEMP, resolution=-1, orient=HORIZONTAL)
        self.slider_api_temperature.pack(anchor=CENTER)

        # API Min temperature slider
        self.api_min_temp_lbl = Label(self.window, text="API Min Temperature")
        self.api_min_temp_lbl.pack(anchor=CENTER)
        self.api_min_temp_var = DoubleVar(value=cnst.DEFAULT_TEMP)
        self.slider_api_min_temperature = Scale(
            self.window, variable=self.api_min_temp_var, from_=cnst.MIN_TEMP, to_=cnst.MAX_TEMP, resolution=-1, orient=HORIZONTAL)
        self.slider_api_min_temperature.pack(anchor=CENTER)

        # API Max temperature slider
        self.api_max_temp_lbl = Label(self.window, text="API Max Temperature")
        self.api_max_temp_lbl.pack(anchor=CENTER)
        self.api_max_temp_var = DoubleVar(value=cnst.DEFAULT_TEMP)
        self.slider_api_max_temperature = Scale(
            self.window, variable=self.api_max_temp_var, from_=cnst.MIN_TEMP, to_=cnst.MAX_TEMP, resolution=-1, orient=HORIZONTAL)
        self.slider_api_max_temperature.pack(anchor=CENTER)

        # API Feels temperature slider
        self.api_feels_temp_lbl = Label(
            self.window, text="API Feels Temperature")
        self.api_feels_temp_lbl.pack(anchor=CENTER)
        self.api_feels_temp_var = DoubleVar(value=cnst.DEFAULT_TEMP)
        self.slider_api_feels_temperature = Scale(
            self.window, variable=self.api_feels_temp_var, from_=cnst.MIN_TEMP, to_=cnst.MAX_TEMP, resolution=-1, orient=HORIZONTAL)
        self.slider_api_feels_temperature.pack(anchor=CENTER)

        # API Humidity slider
        self.api_humidity_lbl = Label(
            self.window, text="API Humidity")
        self.api_humidity_lbl.pack(anchor=CENTER)
        self.api_humidity_var = DoubleVar(value=cnst.DEFAULT_HUMIDITY)
        self.slider_api_humidity = Scale(
            self.window, variable=self.api_humidity_var, from_=cnst.MIN_HUMIDITY, to_=cnst.MAX_HUMIDITY, resolution=-1, orient=HORIZONTAL)
        self.slider_api_humidity.pack(anchor=CENTER)

        # Luminosity slider
        self.luminosity_lbl = Label(self.window, text="Luminosity")
        self.luminosity_lbl.pack(anchor=CENTER)
        self.luminosity_var = DoubleVar(value=cnst.DEFAULT_LUMINOSITY)
        self.slider_luminosity = Scale(self.window, variable=self.luminosity_var,
                                       from_=cnst.MIN_LUMINOSITY, to_=cnst.MAX_LUMINOSITY, resolution=-1, orient=HORIZONTAL)
        self.slider_luminosity.pack(anchor=CENTER)

        # Humidity slider
        self.humidity_lbl = Label(self.window, text="Humidity")
        self.humidity_lbl.pack(anchor=CENTER)
        self.humidity_var = DoubleVar(value=cnst.DEFAULT_HUMIDITY)
        self.slider_humidity = Scale(self.window, variable=self.humidity_var,
                                     from_=cnst.MIN_HUMIDITY, to_=cnst.MAX_HUMIDITY, resolution=-1, orient=HORIZONTAL)
        self.slider_humidity.pack(anchor=CENTER)

        # Noise slider
        self.noise_lbl = Label(self.window, text="Noise")
        self.noise_lbl.pack(anchor=CENTER)
        self.noise_var = DoubleVar(value=cnst.DEFAULT_NOISE)
        self.slider_noise = Scale(self.window, variable=self.noise_var,
                                  from_=cnst.MIN_NOISE, to_=cnst.MAX_NOISE, resolution=-1, orient=HORIZONTAL)
        self.slider_noise.pack(anchor=CENTER)

        # Air Quality slider
        self.airquality_lbl = Label(self.window, text="AirQuality")
        self.airquality_lbl.pack(anchor=CENTER)
        self.airquality_var = DoubleVar(value=cnst.DEFAULT_AIRQUALITY)
        self.slider_airquality = Scale(self.window, variable=self.airquality_var,
                                       from_=cnst.MIN_AIRQUALITY, to_=cnst.MAX_AIRQUALITY, resolution=-1, orient=HORIZONTAL)
        self.slider_airquality.pack(anchor=CENTER)

        # Button to publish the data
        self.submit_btn = Button(
            self.window, text="Simulate", command=self.publish)
        self.submit_btn.pack(anchor=CENTER)

    def publish(self):
        self.client.publish(topic="temperature",
                            payload=self.slider_temperature.get(), qos=1)
        self.client.publish(topic="weather_temp",
                            payload=self.slider_api_temperature.get(), qos=1)
        self.client.publish(topic="weather_temp_min",
                            payload=self.slider_api_min_temperature.get(), qos=1)
        self.client.publish(topic="weather_temp_max",
                            payload=self.slider_api_max_temperature.get(), qos=1)
        self.client.publish(topic="weather_temp_feels",
                            payload=self.slider_api_feels_temperature.get(), qos=1)
        self.client.publish(topic="weather_humidity",
                            payload=self.slider_api_humidity.get(), qos=1)
        self.client.publish(topic="luminosity",
                            payload=self.slider_luminosity.get(), qos=1)
        self.client.publish(topic="humidity",
                            payload=self.slider_humidity.get(), qos=1)
        self.client.publish(topic="noise",
                            payload=self.slider_noise.get(), qos=1)
        self.client.publish(topic="airquality",
                            payload=self.slider_airquality.get(), qos=1)
        self.client.loop()

    def loop(self):
        self.window.mainloop()


if __name__ == "__main__":
    s = Simulator()
    s.loop()
