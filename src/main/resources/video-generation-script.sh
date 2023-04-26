#ffmpeg -loop 1 -i image1.png -i input.mp3 -vf "zoompan=z='min(max(zoom,pzoom)+0.0006,1.3)':d=125, fade=t=in:st=0:d=1:alpha=1, fade=t=out:st=14:d=1:alpha=1, drawtext=text='The Roman Republic was known for its infrastructure':fontcolor=white:fontsize=40:x=(w-text_w)/2:y=(h-text_h)/2:enable='between(t,1,8)', drawtext=text='And It built roads, aqueducts, and public works':fontcolor=black:fontsize=40:x=(w-text_w)/2:y=(h-text_h)/2:enable='between(t,9,16)'" -c:a copy -shortest -t 16 output.mp4
#ffmpeg -loop 1 -i image1.png -i input.mp3 -vf "zoompan=z='min(max(zoom,pzoom)+0.0006,1.3)':d=125, fade=t=in:st=0:d=1:alpha=1, fade=t=out:st=14:d=1:alpha=1, drawbox=y=ih/2-25:color=white@0.5:width=iw:height=50:t=fill, drawtext=text='The Roman Republic was known for its infrastructure':fontcolor=white:fontsize=40:x=(w-text_w)/2:y=(h-text_h)/2:enable='between(t,1,8)', drawbox=y=ih/2-25:color=black@0.5:width=iw:height=50:t=fill, drawtext=text='And It built roads, aqueducts, and public works':fontcolor=black:fontsize=40:x=(w-text_w)/2:y=(h-text_h)/2:enable='between(t,9,16)'" -c:a copy -shortest -t 16 output.mp4
#ffmpeg -loop 1 -i 'https://media.wired.com/photos/598e35994ab8482c0d6946e0/master/w_2240,c_limit/phonepicutres-TA.jpg' -i https://gunmarket.fra1.digitaloceanspaces.com/Roman%20Republic.mp3 -i https://gunmarket.fra1.digitaloceanspaces.com/speach-1.7735688113823094.mp3 -map 0:v:0 -map 1:a:0 -map 2:a:0 -vf "scale=1920:1080,format=rgba,zoompan=z='min(max(zoom,pzoom)+0.0006,1.3)':d=125,fade=t=in:st=0:d=1:alpha=1,fade=t=out:st=14:d=1:alpha=1,drawbox=y=ih/2-25:color=black@0.5:width=iw:height=50:t=fill:enable='between(t,0,7)',drawbox=y=ih/2-25:color=black@0.5:width=iw:height=50:t=fill:enable='between(t,8,14)',drawtext=text='The Roman Republic was known for its infrastructure':fontcolor=white:fontsize=40:x=(w-text_w)/2:y=(h-text_h)/2:enable='between(t,0,7)',drawtext=text='And It built roads, aqueducts, and public works':fontcolor=white:fontsize=40:x=(w-text_w)/2:y=(h-text_h)/2:enable='between(t,8,14)'" -c:a copy -shortest -t 14 output2.mp4
ffmpeg -loop 1 -i 'https://media.wired.com/photos/598e35994ab8482c0d6946e0/master/w_2240,c_limit/phonepicutres-TA.jpg' -i https://gunmarket.fra1.digitaloceanspaces.com/Roman%20Republic.mp3 -i https://gunmarket.fra1.digitaloceanspaces.com/speach-1.7735688113823094.mp3 -filter_complex amerge=inputs=2 -vf "scale=1920:1080,format=rgba,zoompan=z='min(max(zoom,pzoom)+0.0006,1.3)':d=125,fade=t=in:st=0:d=1:alpha=1,fade=t=out:st=14:d=1:alpha=1,drawbox=y=ih/2-25:color=black@0.5:width=iw:height=50:t=fill:enable='between(t,0,7)',drawbox=y=ih/2-25:color=black@0.5:width=iw:height=50:t=fill:enable='between(t,8,14)',drawtext=text='The Roman Republic was known for its infrastructure':fontcolor=white:fontsize=40:x=(w-text_w)/2:y=(h-text_h)/2:enable='between(t,0,7)',drawtext=text='And It built roads, aqueducts, and public works':fontcolor=white:fontsize=40:x=(w-text_w)/2:y=(h-text_h)/2:enable='between(t,8,14)'" -shortest -t 14 output2.mp4
