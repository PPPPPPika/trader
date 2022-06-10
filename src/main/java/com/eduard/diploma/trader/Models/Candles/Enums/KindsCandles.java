package com.eduard.diploma.trader.Models.Candles.Enums;

import com.binance.api.client.domain.market.CandlestickInterval;

public enum KindsCandles {
    ONE {
        @Override
        public int getDurationInterval() {
            return 60;
        }

        @Override
        public CandlestickInterval getCandlestickInterval() {
            return CandlestickInterval.ONE_MINUTE;
        }
    },
    THREE {
        @Override
        public int getDurationInterval() {
            return 180;
        }

        @Override
        public CandlestickInterval getCandlestickInterval() {
            return CandlestickInterval.THREE_MINUTES;
        }
    },
    FIVE {
        @Override
        public int getDurationInterval() {
            return 300;
        }

        @Override
        public CandlestickInterval getCandlestickInterval() {
            return CandlestickInterval.FIVE_MINUTES;
        }
    },
    FIFTEEN {
        @Override
        public int getDurationInterval() {
            return 900;
        }

        @Override
        public CandlestickInterval getCandlestickInterval() {
            return CandlestickInterval.FIFTEEN_MINUTES;
        }
    },
    THIRTY {
        @Override
        public int getDurationInterval() {
            return 1800;
        }

        @Override
        public CandlestickInterval getCandlestickInterval() {
            return CandlestickInterval.HALF_HOURLY;
        }
    },
    SIXTY {
        @Override
        public int getDurationInterval() {
            return 3600;
        }

        @Override
        public CandlestickInterval getCandlestickInterval() {
            return CandlestickInterval.HOURLY;
        }
    };
    public abstract int getDurationInterval();
    public abstract CandlestickInterval getCandlestickInterval();
}
