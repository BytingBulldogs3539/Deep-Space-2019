# Untitled - By: Cameron - Mon Jan 14 2019


import sensor, image, pyb, os, time, math, mjpeg
from pyb import LED

blue_led  = LED(3)

TRIGGER_THRESHOLD = 5

BG_UPDATE_FRAMES = 1 # How many frames before blending.

thresholds=[69,255]

knownHeight = 150.5

#focal_mm = 2.8
#image_width_in_pixels=160
#sensor_width_mm=3.984
#FOV= 70.8

#calcFocalLength = (focal_mm / sensor_width_mm) * image_width_in_pixels
calcFocalLength = 284.745



sensor.reset() # Initialize the camera sensor.
sensor.set_pixformat(sensor.GRAYSCALE) # or sensor.RGB565
sensor.set_framesize(sensor.QVGA) # or sensor.QQVGA (or others)
sensor.skip_frames(time = 2000) # Let new settings take affect.
sensor.set_auto_whitebal(False) # Turn off white balance.
sensor.set_auto_exposure(False, exposure_us = 1)
clock = time.clock() # Tracks FPS.

IR_fb = sensor.alloc_extra_fb(sensor.width(), sensor.height(), sensor.GRAYSCALE)
NON_fb = sensor.alloc_extra_fb(sensor.width(), sensor.height(), sensor.GRAYSCALE)

g = pyb.Pin('P7', pyb.Pin.OUT_PP)

sensor.set_hmirror(True)
sensor.set_vflip(True)


ShouldIR=True
frame_count = 0
video_frame_Count = 1650
label_count = 0
#f=open('/video'+str(label_count)+".txt",'w')
#m = mjpeg.Mjpeg("video"+str(label_count)+".mjpeg")
i=0

while(True):
    frame_count = frame_count+1
    blue_led.on()
    clock.tick() # Track elapsed milliseconds between snapshots().
    if(ShouldIR):
        g.value(True)
    img = sensor.snapshot()
    ##img.lens_corr(1.8)
    g.value(False)
    if(ShouldIR):
        IR_fb.replace(img)
        img.difference(NON_fb)
    else:
        NON_fb.replace(img)
        img.difference(IR_fb)

    blobs=img.find_blobs([thresholds], area_threshold=100, merge=False)
    averageArea = 0
    for blob in blobs:
        rotation = (blob.rotation()-1.5708)*180/math.pi
        if(not(abs(rotation)>=5 and (abs(rotation)<=60))):
            blobs.remove(blob)
            continue

        averageArea+=blob.area()
    if(len(blobs)>0):
        averageArea = averageArea/len(blobs)
    closestBlob = None
    distanceFromCenter = sensor.width()
    for blob in blobs:
        if(blob.area()+15>averageArea):
            if (distanceFromCenter>abs(blob.cx()-sensor.width()/2.0)):
                 closestBlob=blob
                 distanceFromCenter = abs(blob.cx()-sensor.width()/2.0)

    if(not (closestBlob==None)):
        img.draw_rectangle(closestBlob.rect(), color = (100,100,100), thickness = 2, fill = False)
        blobs.remove(closestBlob)

        distanceBetweenBlobs = abs((closestBlob.rotation()-1.5708)*sensor.width())
        secondBestBlob=None

        for blob in blobs:
            if((closestBlob.rotation()-1.5708)*(blob.rotation()-1.5708)<0):
                if((blob.cx()-closestBlob.cx())*(closestBlob.rotation()-1.5708)<distanceBetweenBlobs and (blob.cx()-closestBlob.cx())*(closestBlob.rotation()-1.5708)>0):
                    totalRatio = closestBlob.h()/abs(closestBlob.cx()-blob.cx())
                    if(totalRatio>.25 and totalRatio<=.9):
                        secondBestBlob = blob
                        distanceBetweenBlobs = (blob.cx()-closestBlob.cx())*(closestBlob.rotation()-1.5708)


        if(not (secondBestBlob==None)):

            img.draw_rectangle(secondBestBlob.rect(), color = (100,255,255), thickness = 2, fill = False)
            centerOfBothX = ((secondBestBlob.cx()+closestBlob.cx())/2.0)
            img.draw_line(int(centerOfBothX), 0, int(centerOfBothX), 60, color = (255, 255, 255), thickness = 2)
            offsetPercent = (((secondBestBlob.cx()+closestBlob.cx())/2.0)-(sensor.width()/2.0))/(sensor.width()/2.0)
            print(str(offsetPercent)+',')
            #f.write(str(offsetPercent) + ' ')                               # write pixel values to the open file f
            #f.write('\r\n')
            #i = i+1
            #img.save("temp/"+str(i)+".bmp")
        else:
            print(0.0,)
    else:
        print(0.0,)


        #if (blob.h()>10 and blob.w()>10):

            ##
            ##print((blob.rotation()-1.5708)*180/math.pi)
            #blobs.remove(blob)
            #if(((
            #blob.rotation()-1.5708)*180/math.pi)>=10 and ((blob.rotation()-1.5708)*180/math.pi)<=20 or ((blob.rotation()-1.5708)*180/math.pi)<=-10 and ((blob.rotation()-1.5708)*180/math.pi)>=-20):
                #if((blob.w()/blob.h())>=.4 and (blob.w()/blob.h())<=.8):
                    #for blobx in blobs:
                        #if (blobx.h()>10 and blob.w()>10):

                            #if(((blobx.rotation()-1.5708)*180/math.pi)>=10 and ((blobx.rotation()-1.5708)*180/math.pi)<=20 or ((blobx.rotation()-1.5708)*180/math.pi)<=-10 and ((blobx.rotation()-1.5708)*180/math.pi)>=-20):
                                #if((blobx.w()/blobx.h())>=.4 and (blobx.w()/blobx.h())<=.8):
                                    ##print("ACCEPT", (blob.rotation()-1.5708)*180/math.pi, blob.w()/blob.h(), "ACCEPT", (blobx.rotation()-1.5708)*180/math.pi, blobx.w()/blobx.h())
                                    #print(((knownHeight*calcFocalLength)/blob.h()), clock.fps())
                                    #print((blobx.cx()+blob.cx())/2.0-sensor.width()/2.0)
                                    #print(blob.h())
                                    ##print(math.acos(blob.w()/(0.55*blob.h())))
    #print(clock.fps())
    ShouldIR = not ShouldIR
    #m.add_frame(img)

    #if(frame_count>video_frame_Count):
        ##m.close(clock.fps())
        #f.close()

        #frame_count = 0
        #label_count = label_count+1
        #f=open('/video'+str(label_count)+".txt",'w')
        ##m = mjpeg.Mjpeg("video"+str(label_count)+".mjpeg")

