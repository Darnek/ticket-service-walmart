export interface HoldSeat{
    id: number,
    customerId: number,
    holdTimenumber: Date,
    customerEmail: string,
    seatsHolded: number[],
    active: boolean
}