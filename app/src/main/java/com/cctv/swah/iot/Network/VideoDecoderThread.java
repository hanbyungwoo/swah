package com.cctv.swah.iot.Network;

import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.util.Log;
import android.view.Surface;

import java.io.IOException;
import java.nio.ByteBuffer;

public class VideoDecoderThread extends Thread {
	private static final String VIDEO = "video/";
	private static final String TAG = "VideoDecoder";
	private MediaFormat format;
	private MediaCodec codec;
	MediaExtractor mExtractor;

	private boolean eosReceived;

	// str
	// byte[] bb = null;
	// bb = str.getBytes();
	String content;

	public boolean init(Surface surface, String text) {
		String mime = "video/avc";
		int a =0;
		content = text;
		try {
			Log.e("TAG", "에러에레에러 : " + a);
			a = 1;
			format = MediaFormat.createVideoFormat(mime, 300, 300);
			Log.e("TAG", "에러에레에러 : " + a);
			a = 2;
			codec = MediaCodec.createDecoderByType(mime);
			Log.e("TAG", "에러에레에러 : " + a);
			a = 3;
			codec.configure(format, surface, null, 0);
			Log.e("TAG", "에러에레에러 : " + a);
			a= 4;
			codec.start();
			Log.e("TAG", "에러에레에러 : " + a);
			start();
		} catch(Exception e) {
			Log.e("TAG", "에러에레에러 : " + a);
		}

//        Log.e("TAG", "init시작");
//		eosReceived = false;
//		content = filePath;
//		try {
//			mExtractor = new MediaExtractor();
//            Log.e("!!!!!", filePath);
//            Log.e("TAG", "init시작_filepath경과_전");
//            Log.e("!!!!!", content);
////			mExtractor.setDataSource(filePath);
//            Log.e("TAG", "init시작_filepath경과");
//			for (int i = 0; i < mExtractor.getTrackCount(); i++) {
//				MediaFormat format = mExtractor.getTrackFormat(i);
//
//				String mime = format.getString("video/avc");
//                Log.e("TAG", "init시작_filepath경과_즁");
////				if (mime.startsWith(VIDEO)) {
//                    Log.e("TAG", "init시작_filepath경과________ㅠㅠㅠ");
//					mExtractor.selectTrack(i);
//					mDecoder = MediaCodec.createDecoderByType(mime);
//					try {
//						Log.d(TAG, "format : " + format);
//						mDecoder.configure(format, surface, null, 0 /* Decoder */);
//                        mDecoder.start();
//
//					} catch (IllegalStateException e) {
//						Log.e(TAG, "codec '" + mime + "' failed configuration. " + e);
//						return false;
//					}
//                    Log.e("TAG", "init시작_start앞에서");
//					mDecoder.start();
//					break;
//				}
////			}
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//        Log.e("TAG", "init끝");
//
//		return true;
		return true;
	}

	@Override
	public void run() {
        Log.e("!!!!!!!!!!!!!!!!", "런런런런런러널너런ㄹ너러너");

		// if API level <= 20, get input and output buffer arrays here
		ByteBuffer[] inputBuffers = codec.getInputBuffers();
		Log.e("TTT", "에러에레에러 : 1");
		ByteBuffer[] outputBuffers = codec.getOutputBuffers();
		Log.e("TTT", "에러에레에러 : 2");
		int inputBufferIndex = codec.dequeueInputBuffer(10000);
		boolean first = false;
		long startWhen = 0;
		BufferInfo info = new BufferInfo();

		if (inputBufferIndex >= 0) {
			// if API level >= 21, get input buffer here
//			ByteBuffer inputBuffer = codec.getInputBuffer(inputBufferIndex);
			byte[] array = content.getBytes();
			ByteBuffer inputBuffer = ByteBuffer.wrap(array);
//			ByteBuffer inputBuffer = inputBuffers[inputIndex];
			// fill inputBuffers[inputBufferIndex] with valid data

			mExtractor = new MediaExtractor();
			int sampleSize = mExtractor.readSampleData(inputBuffer, 0);

			if (mExtractor.advance() && sampleSize > 0) {
				codec.queueInputBuffer(inputBufferIndex, 0, sampleSize, mExtractor.getSampleTime(), 0);

			} else {
				Log.d(TAG, "InputBuffer BUFFER_FLAG_END_OF_STREAM");
				codec.queueInputBuffer(inputBufferIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
//				isInput = false;
			}

			int outIndex = codec.dequeueOutputBuffer(info, 10000);
			switch (outIndex) {
			case MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED:
				Log.d(TAG, "INFO_OUTPUT_BUFFERS_CHANGED");
				codec.getOutputBuffers();
				break;

			case MediaCodec.INFO_OUTPUT_FORMAT_CHANGED:
				Log.d(TAG, "INFO_OUTPUT_FORMAT_CHANGED format : " + codec.getOutputFormat());
				break;

			case MediaCodec.INFO_TRY_AGAIN_LATER:
//				Log.d(TAG, "INFO_TRY_AGAIN_LATER");
				break;

			default:
				if (!first) {
					startWhen = System.currentTimeMillis();
					first = true;
				}
				try {
					long sleepTime = (10000 / 1000) - (System.currentTimeMillis() - startWhen);
					Log.d(TAG, "info.presentationTimeUs : " + (10000 / 1000) + " playTime: " + (System.currentTimeMillis() - startWhen) + " sleepTime : " + sleepTime);

					if (sleepTime > 0)
						Thread.sleep(sleepTime);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				codec.releaseOutputBuffer(outIndex, true /* Surface init */);
				break;
			}

			// All decoded frames have been rendered, we can stop playing now
			if ((1 & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
				Log.d(TAG, "OutputBuffer BUFFER_FLAG_END_OF_STREAM");
//				break;
			}
		}

		codec.stop();
		codec.release();
		mExtractor.release();



		}

		// str
		// byte[] bb = null;
		// bb = str.getBytes();

//		byte[] array = content.getBytes();
//		ByteBuffer info1 = ByteBuffer.wrap(array);
////
////		ByteBuffer[] inputBuffers = ByteBuffer.wrap(array);
//
//
//        BufferInfo info = new BufferInfo();
////        ByteBuffer[] inputBuffers = mDecoder.getInputBuffers();
//        mDecoder.getOutputBuffers();
//
//
//		boolean isInput = true;
//		boolean first = false;
//		long startWhen = 0;
//
//		while (!eosReceived) {
//			if (isInput) {
//				int inputIndex = mDecoder.dequeueInputBuffer(10000);
//				if (inputIndex >= 0) {
//					// fill inputBuffers[inputBufferIndex] with valid data
////					ByteBuffer inputBuffer = inputBuffers[inputIndex];
//                    ByteBuffer inputBuffer = info1;
//
//					int sampleSize = mExtractor.readSampleData(inputBuffer, 0);
//
//					if (mExtractor.advance() && sampleSize > 0) {
//						mDecoder.queueInputBuffer(inputIndex, 0, sampleSize, mExtractor.getSampleTime(), 0);
//
//					} else {
//						Log.d(TAG, "InputBuffer BUFFER_FLAG_END_OF_STREAM");
//						mDecoder.queueInputBuffer(inputIndex, 0, 0, 0, MediaCodec.BUFFER_FLAG_END_OF_STREAM);
//						isInput = false;
//					}
//				}
//			}
//
//			int outIndex = mDecoder.dequeueOutputBuffer(info, 10000);
//			switch (outIndex) {
//			case MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED:
//				Log.d(TAG, "INFO_OUTPUT_BUFFERS_CHANGED");
//				mDecoder.getOutputBuffers();
//				break;
//
//			case MediaCodec.INFO_OUTPUT_FORMAT_CHANGED:
//				Log.d(TAG, "INFO_OUTPUT_FORMAT_CHANGED format : " + mDecoder.getOutputFormat());
//				break;
//
//			case MediaCodec.INFO_TRY_AGAIN_LATER:
////				Log.d(TAG, "INFO_TRY_AGAIN_LATER");
//				break;
//
//			default:
//				if (!first) {
//					startWhen = System.currentTimeMillis();
//					first = true;
//				}
//				try {
//					long sleepTime = (10000 / 1000) - (System.currentTimeMillis() - startWhen);
//					Log.d(TAG, "info.presentationTimeUs : " + (10000 / 1000) + " playTime: " + (System.currentTimeMillis() - startWhen) + " sleepTime : " + sleepTime);
//
//					if (sleepTime > 0)
//						Thread.sleep(sleepTime);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//
//				mDecoder.releaseOutputBuffer(outIndex, true /* Surface init */);
//				break;
//			}
//
//			// All decoded frames have been rendered, we can stop playing now
//			if ((1 & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
//				Log.d(TAG, "OutputBuffer BUFFER_FLAG_END_OF_STREAM");
//				break;
//			}
//		}
//
//		mDecoder.stop();
//		mDecoder.release();
//		mExtractor.release();
//	}

	public void close() {
		eosReceived = true;
	}
}
