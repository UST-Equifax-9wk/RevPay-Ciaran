import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'formatCard',
  standalone: true
})
export class FormatCardPipe implements PipeTransform {

  transform(value: string): string {
    // apparently not all credit cards are 16 digits so instead we will just censor most of the number
    return 'x' + value.substring(value.length - 4);
  }

}
