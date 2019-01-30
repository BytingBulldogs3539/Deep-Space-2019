#include <Adafruit_NeoPixel.h>

#define PIN 6   // input pin Neopixel is attached to

#define NUMPIXELS      75 // number of neopixels in strip

Adafruit_NeoPixel pixels = Adafruit_NeoPixel(NUMPIXELS, PIN, NEO_GRB + NEO_KHZ800);


int color = 3; //Sets the lights to a color between 0-6, following the standard 7 color ROYGBIV order. if more than 6, also applies a fade effect.

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

  if(color == 0 || color == 7)
    {    //Error Red
    pixels.setPixelColor(i, pixels.Color(255, 0, 0));
    }
  else if(color == 1 || color == 8)
    {    //Cargo Orange
    pixels.setPixelColor(i, pixels.Color(215, 105, 0));
    }
  else if(color == 2 || color == 9)
    {    //Hatch Yellow
    pixels.setPixelColor(i, pixels.Color(255, 255, 0));
    }
  else if(color == 3 || color == 10)
    {    //Bulldog Green
    pixels.setPixelColor(i, pixels.Color(0, 255, 0));
    }
  else if(color == 4 || color == 11)
    {    //Unsued Blue
    pixels.setPixelColor(i, pixels.Color(0, 0, 255));
    }
  else if(color == 5 || color == 12)
    {    //Fault Purple
    pixels.setPixelColor(i, pixels.Color(128, 0, 128));
    }
  else if(color == 6 || color == 13)
    {    //Usless Magenta
    pixels.setPixelColor(i, pixels.Color(255, 0, 255));
    }
  else //(less than 0 or greater than 13)
    { //off
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
