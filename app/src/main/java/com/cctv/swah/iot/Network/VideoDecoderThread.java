package com.cctv.swah.iot.Network;

//
//import android.media.MediaCodec;
//import android.media.MediaExtractor;
//import android.media.MediaFormat;
//import android.util.Log;
//import android.view.Surface;
//
//import java.io.IOException;
//import java.nio.ByteBuffer;
//
//public class VideoDecoderThread extends Thread {
//	private static final String VIDEO = "video/";
//	private static final String TAG = "VideoDecoder";
//	private MediaExtractor mExtractor;
//	private MediaCodec mDecoder;
//
//	private boolean eosReceived;
//
//	public boolean init(Surface surface, String filePath) {
//		eosReceived = false;
//		try {
//			mExtractor = new MediaExtractor();
//			mExtractor.setDataSource(filePath);
//
//			for (int i = 0; i < mExtractor.getTrackCount(); i++) {
//				MediaFormat format = mExtractor.getTrackFormat(i);
//
//				String mime = format.getString(MediaFormat.KEY_MIME);
//				if (mime.startsWith(VIDEO)) {
//					mExtractor.selectTrack(i);
//					mDecoder = MediaCodec.createDecoderByType(mime);
//					try {
//						Log.d(TAG, "format : " + format);
//						mDecoder.configure(format, surface, null, 0 /* Decoder */);
//
//					} catch (IllegalStateException e) {
//						Log.e(TAG, "codec '" + mime + "' failed configuration. " + e);
//						return false;
//					}
//
//					mDecoder.start();
//					break;
//				}
//			}
//
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return true;
//	}
//
//	@Override
//	public void run() {
//
//		boolean isInput = true;
//		boolean first = false;
//		long startWhen = 0;
//		ByteBuffer[] inputBuffers = mDecoder.getInputBuffers();
//		Log.e("TAGAGGAGAGAG", inputBuffers+"::???????????");
//		ByteBuffer[] outputBuffers = mDecoder.getOutputBuffers();
//
//		MediaCodec.BufferInfo info = new MediaCodec.BufferInfo();
//
//		while (!eosReceived) {
//			if (isInput) {
//				int inputIndex = mDecoder.dequeueInputBuffer(10000);
//				if (inputIndex >= 0) {
//					// fill inputBuffers[inputBufferIndex] with valid data
//					ByteBuffer inputBuffer = inputBuffers[inputIndex];
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
//				case MediaCodec.INFO_OUTPUT_BUFFERS_CHANGED:
//					Log.d(TAG, "INFO_OUTPUT_BUFFERS_CHANGED");
//					mDecoder.getOutputBuffers();
//					break;
//
//				case MediaCodec.INFO_OUTPUT_FORMAT_CHANGED:
//					Log.d(TAG, "INFO_OUTPUT_FORMAT_CHANGED format : " + mDecoder.getOutputFormat());
//					break;
//
//				case MediaCodec.INFO_TRY_AGAIN_LATER:
////				Log.d(TAG, "INFO_TRY_AGAIN_LATER");
//					break;
//
//				default:
//					if (!first) {
//						startWhen = System.currentTimeMillis();
//						first = true;
//					}
//					try {
//						long sleepTime = (info.presentationTimeUs / 1000) - (System.currentTimeMillis() - startWhen);
//						Log.d(TAG, "info.presentationTimeUs : " + (info.presentationTimeUs / 1000) + " playTime: " + (System.currentTimeMillis() - startWhen) + " sleepTime : " + sleepTime);
//
//						if (sleepTime > 0)
//							Thread.sleep(sleepTime);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//
//					mDecoder.releaseOutputBuffer(outIndex, true /* Surface init */);
//					break;
//			}
//
//			// All decoded frames have been rendered, we can stop playing now
//			if ((info.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
//				Log.d(TAG, "OutputBuffer BUFFER_FLAG_END_OF_STREAM");
//				break;
//			}
//		}
//
//		mDecoder.stop();
//		mDecoder.release();
//		mExtractor.release();
//	}
//
//	public void close() {
//		eosReceived = true;
//	}
//}



import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaCodecInfo;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.util.Log;
import android.view.Surface;

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
		content = text;
		try {

			format = MediaFormat.createVideoFormat(mime, 160, 120);

			format.setInteger(MediaFormat.KEY_BIT_RATE, 125000);
			format.setInteger(MediaFormat.KEY_FRAME_RATE, 15);
			format.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatYUV420Planar);
			format.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, 5);


			codec = MediaCodec.createDecoderByType(mime);
			codec.configure(format, surface, null, 0);
			codec.start();
			start();
		} catch(Exception e) {
			Log.e("TAG", "에러");
		}

		return true;
	}

	@Override
	public void run() {
		// if API level <= 20, get input and output buffer arrays here
		ByteBuffer[] inputBuffers = codec.getInputBuffers();
		Log.e("TAGAGGAGAGAG", inputBuffers+"::???????????");
		ByteBuffer[] outputBuffers = codec.getOutputBuffers();
		Log.e("TAGAGGAGAGAG", outputBuffers+"::???????????");
		int inputBufferIndex = codec.dequeueInputBuffer(10000);
		boolean first = false;
		long startWhen = 0;
		BufferInfo info = new BufferInfo();

		while (true) {
			if (inputBufferIndex >= 0) {
				// if API level >= 21, get input buffer here
//			ByteBuffer inputBuffer = codec.getInputBuffer(inputBufferIndex);
				byte[] array = content.getBytes();

				Log.e("TAGTAG", array.toString() + "메세지");
				ByteBuffer inputBuffer = ByteBuffer.wrap(array);
				Log.e("TAGTAG", inputBuffer + "메세지");
//			ByteBuffer inputBuffer = inputBuffers[inputIndex];
				// fill inputBuffers[inputBufferIndex] with valid data

				mExtractor = new MediaExtractor();
				int sampleSize = mExtractor.readSampleData(inputBuffer, 0);

				Log.e("TAGTAG", sampleSize + "메세지");

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
							long sleepTime = (info.presentationTimeUs / 1000) - (System.currentTimeMillis() - startWhen);
							Log.d(TAG, "info.presentationTimeUs : " + (info.presentationTimeUs / 1000) + " playTime: " + (System.currentTimeMillis() - startWhen) + " sleepTime : " + sleepTime);

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
				if ((info.flags & MediaCodec.BUFFER_FLAG_END_OF_STREAM) != 0) {
					Log.d(TAG, "OutputBuffer BUFFER_FLAG_END_OF_STREAM");
//				break;
				}
			}

			codec.stop();
			codec.release();
			mExtractor.release();


		}
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
///////////////////////////////////////////////////////////////////////////////

