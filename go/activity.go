package app

import (
	"context"
	"fmt"
)

func BookHotel(ctx context.Context, info BookingInfo) error {
	fmt.Printf("Booking Hotel, transaction ID: %s\n", info.ClientId)
	return nil
}

func BookFlight(ctx context.Context, info BookingInfo) error {
	fmt.Printf("Booking Flight, transaction ID: %s\n", info.ClientId)
	return nil
}

func BookExcursion(ctx context.Context, info BookingInfo) error {
	fmt.Printf("Booking Excursion, transaction ID: %s\n", info.ClientId)
	return nil
}

func CancelHotel(ctx context.Context, idempotencyKey string) error {
	fmt.Printf("Canceling Hotel with transaction ID: %s\n", idempotencyKey)
	return nil
}

func CancelFlight(ctx context.Context, idempotencyKey string) error {
	fmt.Printf("Canceling Flight with transaction ID: %s\n", idempotencyKey)
	return nil
}

func CancelExcursion(ctx context.Context, idempotencyKey string) error {
	fmt.Printf("Canceling Excursion with transaction ID: %s\n", idempotencyKey)
	return nil
}
