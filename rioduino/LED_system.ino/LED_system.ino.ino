#include <Adafruit_NeoPixel.h>

#define PIN 6   // input pin Neopixel is attached to

#define NUMPIXELS      75 // number of neopixels in strip

Adafruit_NeoPixel pixels = Adafruit_NeoPixel(NUMPIXELS, PIN, NEO_GRB + NEO_KHZ800);


int color = 10; //Sets the lights to a color between 0-6, following the standard 7 color ROYGBIV order. if more than 6, also applies a fade effect.
//Default color = 3

bool isAdding = true; //If true, fade increases; if false, fade decreases.
int storeBrightness = 0; //Store brightness value

void setup() {
  // Initialize the NeoPixel library.
  pixels.begin();
  Serial.begin(9600);
}

void loop() {

  if(color > 6) //If color needs a fade
  {
    if(isAdding) //is adding?
    {
      storeBrightness++; //increase brightness
    }
    else //is decrasing?
    {
      storeBrightness--; //decrease brightness
    }
    delay(5);
  }

  if(storeBrightness > 99) //if brightness is 100 or more (somehow)
  {
    isAdding = false; //start subtracting
  }
  else if(storeBrightness < 1) //if brightness is less than 0 or more (somehow)
  {
    isAdding = true; //start adding (default)
  }
  
  for (int i=0; i < NUMPIXELS; i++) {
    // pixels.Color takes RGB values, from 0,0,0 up to 255,255,255

  if(color%7 == 0)
    {    //Error Red
    pixels.setPixelColor(i, pixels.Color(255, 0, 0));
    }
  else if(color%7 == 1)
    {    //Cargo Orange
    pixels.setPixelColor(i, pixels.Color(220, 60, 0));
    }
  else if(color%7 == 2)
    {    //Hatch Yellow
    pixels.setPixelColor(i, pixels.Color(200, 130, 0));
    }
  else if(color%7 == 3)
    {    //Bulldog Green  (0, 255, 0)
    pixels.setPixelColor(i, pixels.Color(0, 255, 0));
    }
  else if(color%7 == 4)
    {    //Unused Blue
    pixels.setPixelColor(i, pixels.Color(0, 0, 255));
    }
  else if(color%7 == 5)
    {    //Fault Purple
    pixels.setPixelColor(i, pixels.Color(100, 0, 210));
    }
  else if(color%7 == 6)
    {    //Usless Magenta
    pixels.setPixelColor(i, pixels.Color(255, 0, 255));
    }
  else //(less than 0 or greater than 13)
    {    //Off Black
    pixels.setPixelColor(i, pixels.Color(0, 0, 0));
    }
    
   if(color > 6) //if the color value calls for a fade effect
      {
       pixels.setBrightness(storeBrightness); //Set the brightness to the brightness (d u h)
      }
   else
     {
      pixels.setBrightness(100); //Lock it at 100
     }
   }

    // This sends the updated pixel color to the hardware.
    pixels.show();

  }

void serialEvent() {
  while (Serial.available()) {
    char c = Serial.read();
    color = (unsigned int)c;
  }
}
