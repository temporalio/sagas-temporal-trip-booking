package app

import (
	"context"
	"fmt"
	"time"
)

func BookHotel(ctx context.Context, bookingInfo BookingInfo, start time.Time, end time.Time, idempotencyKey string) error {
	fmt.Println("Booking Hotel")
	return nil
}

func BookFlight(ctx context.Context, bookingInfo BookingInfo, start time.Time, end time.Time, idempotencyKey string) error {
	fmt.Println("Booking Flight")
	return nil
}

func BookExcursion(ctx context.Context, bookingInfo BookingInfo, start time.Time, end time.Time, idempotencyKey string) error {
	fmt.Println("Booking Excursion")
	return nil
}

func CancelHotel(ctx context.Context, idempotencyKey string) error {
	fmt.Println("Canceling Hotel")
	return nil
}

func CancelFlight(ctx context.Context, idempotencyKey string) error {
	fmt.Println("Canceling Flight")
	return nil
}

func CancelExcursion(ctx context.Context, idempotencyKey string) error {
	fmt.Println("Canceling Excursion")
	return nil
}
