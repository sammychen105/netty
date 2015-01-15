/*
 * Copyright 2015 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */package io.netty.channel.epoll;

import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.DefaultChannelConfig;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.MessageSizeEstimator;
import io.netty.channel.RecvByteBufAllocator;

import java.util.Map;

public final class EpollDomainChannelConfig extends DefaultChannelConfig {
    private volatile EpollChannelOption.EpollDomainSocketReadMode mode =
            EpollChannelOption.EpollDomainSocketReadMode.BYTES;

    EpollDomainChannelConfig(Channel channel) {
        super(channel);
    }

    @Override
    public Map<ChannelOption<?>, Object> getOptions() {
        return getOptions(super.getOptions(), EpollChannelOption.DOMAIN_SOCKET_READ_MODE);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getOption(ChannelOption<T> option) {
        if (option == EpollChannelOption.DOMAIN_SOCKET_READ_MODE) {
            return (T) getReadMode();
        }
        return super.getOption(option);
    }

    @Override
    public <T> boolean setOption(ChannelOption<T> option, T value) {
        validate(option, value);

        if (option == EpollChannelOption.DOMAIN_SOCKET_READ_MODE) {
            setReadMode((EpollChannelOption.EpollDomainSocketReadMode) value);
        } else {
            return super.setOption(option, value);
        }

        return true;
    }
    @Override
    public EpollDomainChannelConfig setMaxMessagesPerRead(int maxMessagesPerRead) {
        super.setMaxMessagesPerRead(maxMessagesPerRead);
        return this;
    }

    @Override
    public EpollDomainChannelConfig setConnectTimeoutMillis(int connectTimeoutMillis) {
        super.setConnectTimeoutMillis(connectTimeoutMillis);
        return this;
    }

    @Override
    public EpollDomainChannelConfig setWriteSpinCount(int writeSpinCount) {
        super.setWriteSpinCount(writeSpinCount);
        return this;
    }

    @Override
    public EpollDomainChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator allocator) {
        super.setRecvByteBufAllocator(allocator);
        return this;
    }

    @Override
    public EpollDomainChannelConfig setAllocator(ByteBufAllocator allocator) {
        super.setAllocator(allocator);
        return this;
    }

    @Override
    public EpollDomainChannelConfig setAutoClose(boolean autoClose) {
        super.setAutoClose(autoClose);
        return this;
    }

    @Override
    public EpollDomainChannelConfig setMessageSizeEstimator(MessageSizeEstimator estimator) {
        super.setMessageSizeEstimator(estimator);
        return this;
    }

    @Override
    public EpollDomainChannelConfig setWriteBufferLowWaterMark(int writeBufferLowWaterMark) {
        super.setWriteBufferLowWaterMark(writeBufferLowWaterMark);
        return this;
    }

    @Override
    public EpollDomainChannelConfig setWriteBufferHighWaterMark(int writeBufferHighWaterMark) {
        super.setWriteBufferHighWaterMark(writeBufferHighWaterMark);
        return this;
    }

    @Override
    public EpollDomainChannelConfig setAutoRead(boolean autoRead) {
        super.setAutoRead(autoRead);
        return this;
    }

    /**
     * Change the {@link EpollChannelOption.EpollDomainSocketReadMode} for the channel. The default is
     * {@link EpollChannelOption.EpollDomainSocketReadMode#BYTES} which means bytes will be read from the
     * {@link Channel} and passed through the pipeline. If
     * {@link EpollChannelOption.EpollDomainSocketReadMode#FILE_DESCRIPTORS} is used
     * {@link FileDescriptor}s will be passed through the {@link ChannelPipeline}.
     *
     * This setting can be modified on the fly if needed.
     */
    public EpollDomainChannelConfig setReadMode(EpollChannelOption.EpollDomainSocketReadMode mode) {
        if (mode == null) {
            throw new NullPointerException("mode");
        }
        this.mode = mode;
        return this;
    }

    /**
     * Return the {@link EpollChannelOption.EpollDomainSocketReadMode} for the channel.
     */
    public EpollChannelOption.EpollDomainSocketReadMode getReadMode() {
        return mode;
    }
}
